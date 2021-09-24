package ssafy;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.lang.Math;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

//import org.apache.hadoop.util.*;

public class TopKSearch {

        public static class Conv implements Comparable<Conv> {

			public double dist;
            public String data;
			public String id;

            public Conv(double d, String id, String data) {
                this.dist = d;
				this.id = id;
                this.data = data;
            }

            @Override
            public int compareTo(Conv o) {
                return this.dist > o.dist ? -1 : (this.dist < o.dist ? 1 : 0);
            }
        }


	/*
	 * Map class 1 (산책 데이터)
	 */

	public static class MapClass1 extends Mapper<Object, Text, Text, Text> {

		private int numOfPartitions = 4;

		private Text emitkey = new Text ();
		private Text emitval = new Text ();

		public void setup (Mapper.Context context)
		{
			Configuration conf = context.getConfiguration ();
			numOfPartitions = conf.getInt ("numberOfPartitions", 2);

		}

		// CSV 파일 읽기
		// --> format = <point id> , <latitude> , <longitude>
		public void map (Object key, Text value, Context context) throws IOException, InterruptedException
		{
			String arr[] = value.toString().split (",");
			StringBuilder sb = new StringBuilder();
			
			// id, 위,경도
			// int id = Integer.parseInt (arr[0]);
			// int partId = id % numOfPartitions;

			// emitkey.set(Integer.toString(partId));
			context.write(new Text("test"), new Text("W,"+value));
		}
	}

    /*
	 * Map class 2 (편의시설 데이터)
	 */

	public static class MapClass2 extends Mapper<Object, Text, Text, Text> {

		private int numOfPartitions = 4;
        private String walkTable;
		private Text emitkey = new Text ();
		private Text emitval = new Text ();


		// CSV 파일 읽기
		// --> format = <point id> , <latitude> , <longitude>
		public void map (Object key, Text value, Context context) throws IOException, InterruptedException
		{
			String arr[] = value.toString().split (",");
			// id, 위,경도
			// int id = Integer.parseInt (arr[0]);
			// int partId = id % numOfPartitions;

			// emitkey.set(Integer.toString(partId));
			context.write(new Text("test"), new Text("C,"+value));
		}
	}


	/*
	 * Reduce class part
	 */
	public static class ReduceClass1 extends Reducer<Text, Text, Text, Text> {

		private int numOfPartitions = 4;
		private int K;
		private String query;

		private Text emitkey = new Text ();
		private Text emitval = new Text ();

		public void setup (Reducer.Context context)
		{
			Configuration conf = context.getConfiguration ();
			K = conf.getInt ("K", 2);
		}

		public void reduce(Text key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException
		{
			StringBuilder sb = new StringBuilder();
			ArrayList<String[]> walkFileValues = new ArrayList<>();
			ArrayList<String[]> convFileValues = new ArrayList<>();
			String[] stringValues;

			double dist;
           

			for(Text p : values){
				stringValues = p.toString().split(",");

				if("W".equals(stringValues[0])){
					walkFileValues.add(Arrays.copyOf(stringValues, stringValues.length));
				}
				if("C".equals(stringValues[0])){
					convFileValues.add(Arrays.copyOf(stringValues, stringValues.length));
				}

			}

			if(walkFileValues.size()>0){
				for(String[] walk : walkFileValues){
					
					PriorityQueue<Conv> queue = new PriorityQueue<Conv>();
					String walkId = walk[1];
					String walkLng = walk[2];
					String walkLat = walk[3];
					for(String[] conv : convFileValues){
						String convAddress = conv[1];
						String convName = conv[2];
						String convLat = conv[3];
						String convLng = conv[4];

						dist = dist(walkLat, walkLng, convLat, convLng);
						if(dist>1000) continue; //거리가 1KM 초과 시 continue

						Conv mt = new Conv(dist, walkId, convAddress+","+convName+","+convLat+","+convLng+","+dist);

						if(queue.size() < K) {
							queue.add(mt);
						}
						else {
							if((queue.peek()).dist > dist){
								queue.remove();
								queue.add(mt);
							}
						}
					}


					//Max Heap에 담긴 편의점 데이터 context에 쓰기
					while(!queue.isEmpty()){
						Conv mt = queue.poll();

						emitkey.set(mt.id);
						emitval.set(mt.data);
						context.write(emitkey, emitval);
					}
				}
			}



		}

		//위-경도 간 거리 구하기
	    public static double dist(String walkLat, String walkLng, String convLat, String convLng) {

			double lat1 = Double.parseDouble(walkLat); 
			double lat2 = Double.parseDouble(convLat); 
			double lng1 = Double.parseDouble(walkLng); 
			double lng2 = Double.parseDouble(convLng); 
            double theta = lng1 -lng2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
			dist = Math.acos(dist);
			dist = rad2deg(dist);
			dist = dist * 60 * 1.1515;

            return dist * 1609.344;
        }

		// This function converts decimal degrees to radians
		private static double deg2rad(double deg) {
			return (deg * Math.PI / 180.0);
		}
     
		// This function converts radians to decimal degrees
		private static double rad2deg(double rad) {
			return (rad * 180 / Math.PI);
		}



	}

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		Configuration conf = new Configuration ();
    		
            String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
            if (otherArgs.length != 5) {
                System.out.println ("usage: <numberOfPartitions> <WalkData> <K> <in> <out>");
                System.exit(1);
            }

                
         	conf.setInt("numberOfPartitions", Integer.parseInt(otherArgs[0]));
            conf.setInt("K", Integer.parseInt(otherArgs[2]));


            FileSystem hdfs = FileSystem.get(conf);
            Path output1 = new Path(otherArgs[4]);
            if (hdfs.exists(output1))
                hdfs.delete(output1, true);


            // Job을 생성
			Job job = new Job (conf, "1st phase");

            // 사용할 jar library를 설정
			job.setJarByClass(TopKSearch.class);

             // 파일입력포맷 지정
            MultipleInputs.addInputPath(job, new Path(otherArgs[1]), TextInputFormat.class, MapClass1.class);
			MultipleInputs.addInputPath(job, new Path(otherArgs[3]), TextInputFormat.class, MapClass2.class);
			

            // 각 클래스 지정
			job.setNumReduceTasks (2);
			//job.setMapperClass(MapClass1.class);
			job.setReducerClass(ReduceClass1.class);

            // 출력 Key, Value타입 지정
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

            //파일출력포맷 지정
            FileOutputFormat.setOutputPath(job, output1);

             //하둡 분산 프로그램 실행
			if (! job.waitForCompletion(true))
			System.exit (1);

	}
}

