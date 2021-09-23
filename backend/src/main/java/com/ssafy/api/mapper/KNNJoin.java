package com.ssafy.api.mapper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;


public class KNN {

    public static class Conv implements WritableComparable<Conv>
    {
        private Double distance = 0.0;
        private String address;
        private String name;
        private Long id = -2l;

        public void set(Double lhs, long rhs)
        {
            distance = lhs;
            id = rhs;
        }

        public Double getDistance()
        {
            return distance;
        }

        public long getId()
        {
            return id;
        }

        //@Override
        public void readFields(DataInput in) throws IOException
        {
            distance = in.readDouble();
            id = in.readLong();
        }

        //@Override
        public void write(DataOutput out) throws IOException
        {
            out.writeDouble(distance);
            out.writeLong(id);
        }

        @Override
        public int compareTo(Conv o) {
            return (this.distance).compareTo(o.distance);
        }
    }


    public static class KnnMapper extends Mapper<Object, Text, NullWritable, DoubleAndLong>
    {
        DoubleAndLong distanceAndId = new DoubleAndLong();
        TreeMap<Double, Long> KnnMap = new TreeMap<Double, Long>();

        // Declaring some variables which will be used throughout the mapper
        int K;

        double sx;
        double sy;
        // Takes a double and returns its squared value.
        private double squaredDistance(double n1)
        {
            return Math.pow(n1,2);
        }

        private double totalSquaredDistance(double x1, double y1) {
            double x = squaredDistance(sx-x1);
            double y = squaredDistance(sy-y1);
            return (x+y) ;
        }

        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            String params = context.getConfiguration().get("Params");
            StringTokenizer st = new StringTokenizer(",");
            //this.K = context.getConfiguration().getInt("k", 10);
            K = Integer.parseInt(st.nextToken());//k);
            System.out.println("K : "+K);
            //String SX = context.getConfiguration().get("x");
            this.sx = Double.parseDouble(st.nextToken());
            //this.sx = context.getConfiguration().getFloat("x", 50.0f);//SX);
            System.out.println("x : "+sx);
            //String SY = context.getConfiguration().get("x");
            this.sy = Double.parseDouble(st.nextToken());
            //this.sy = context.getConfiguration().getFloat("y", 29.0f);//SY);
            System.out.println("y : "+sy);

        }

        @Override
        // The map() method is run by MapReduce once for each row supplied as the input data
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            // Tokenize the input line (presented as 'value' by MapReduce) from the csv file
            // This is the training dataset, R
            String rLine = value.toString();
            StringTokenizer st = new StringTokenizer(rLine, ",");
            long id = Long.parseLong(st.nextToken());
            double x1 = Double.parseDouble(st.nextToken());
            double y1 = Double.parseDouble(st.nextToken());

            // calculate a total squared
            // distance between each pair of corresponding values.
            double tDist = totalSquaredDistance(x1, y1);
            DoubleAndLong did = new DoubleAndLong();
            did.set(tDist, id);

            // Add the total distance and corresponding id for this row into the TreeMap with distance
            // as key and Id as value.
            //KnnMap.put(tDist, rId);
            KnnMap.put(tDist, id);
            // Only K distances are required, so if the TreeMap contains over K entries, remove the last one
            // which will be the highest distance number.
            if (KnnMap.size() > K)
            {
                KnnMap.remove(KnnMap.lastKey());
            }
        }

        @Override
        // The cleanup() method is run once after map() has run for every row
        protected void cleanup(Context context) throws IOException, InterruptedException
        {
            // Loop through the K key:values in the TreeMap
            for(Map.Entry<Double, Long> entry : KnnMap.entrySet())
            {
                Double knnDist = entry.getKey();
                Long knnId = entry.getValue();
                // distanceAndId is the instance of DoubleAndLong declared earlier
                distanceAndId.set(knnDist, knnId);
                // Write to context a NullWritable as key and distanceAndId as value
                context.write(NullWritable.get(), distanceAndId);
            }
        }

        public static class KnnReducer extends Reducer<NullWritable, DoubleAndLong, NullWritable, Text>
        {
            TreeMap<Double, Long> KnnMap = new TreeMap<Double, Long>();
            int K;

            @Override
            // setup() is run before the reduce() method to get the value of K
            protected void setup(Context context) throws IOException, InterruptedException
            {
                K = context.getConfiguration().getInt("k", 10);
            }

            @Override
            // The reduce() method accepts the objects the mapper wrote to context: a NullWritable and a DoubleAndLong object
            public void reduce(NullWritable key, Iterable<DoubleAndLong> values, Context context) throws IOException, InterruptedException
            {
                // values are the K DoubleAndLong objects which the mapper wrote to context
                // We go through each of the value
                for (DoubleAndLong val : values)
                {
                    long rId = val.getId();
                    double tDist = val.getDistance();

                    // Create a TreeMap with the distance and id information for each
                    // DoubleAndLong object and select the K closest ones.
                    KnnMap.put(tDist, rId);
                    if (KnnMap.size() > K)
                    {
                        KnnMap.remove(KnnMap.lastKey());
                    }
                }


                // Write to context another NullWritable key and the distance along with Id of the point as the values for the keys.
                context.write(NullWritable.get(), new Text(KnnMap.toString()));	//We can now see all K nearest neighbours, their distances and corresponding id.
            }
        }


        public static void main(String[] args) throws Exception {
            Configuration conf = new Configuration();
            Job job = Job.getInstance(conf, "KNN");
            job.setJarByClass(KNN.class);
            job.setMapperClass(KnnMapper.class);
            //job.setNumReduceTasks(0);
            job.setReducerClass(KnnReducer.class);
            job.setMapOutputKeyClass(NullWritable.class);
            job.setMapOutputValueClass(DoubleAndLong.class);
            job.setOutputKeyClass(NullWritable.class);
            job.setOutputValueClass(Text.class);
            job.setInputFormatClass(TextInputFormat.class);
            if(args.length < 5) {
                System.err.println("Please input : InputFile_Path, OutputFile_Path, X-coordinate_of_queryPoint, Y-coordinate_of_queryPoint, Value_of_K");
                System.exit(1);
            }
            Path input = new Path(args[0]);//"/Users/shwetimahajan/Downloads/points");
            FileInputFormat.addInputPath(job, input);
            Path output = new Path( args[1]);//"outputKnn1");
            FileOutputFormat.setOutputPath(job, output);
            job.setNumReduceTasks(2);
            StringBuilder sb = new StringBuilder();
//        Float x = Float.parseFloat(args[2]);//50.0f;
//        Float y = Float29.0f;//args[3];
//        Integer k = 10;//args[4];
            sb.append(args[4]); sb.append(",");//value of k
            sb.append(args[2]);sb.append(",");//value of x
            sb.append(args[3]);//Value of y
            job.getConfiguration().set("Params",sb.toString());
//        job.getConfiguration().setInt("k", k);
//        job.getConfiguration().setFloat("x", x);//for some reason my version of Hadoop does not support setDouble()
//        job.getConfiguration().setFloat("y", y);
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        }
    }
}
