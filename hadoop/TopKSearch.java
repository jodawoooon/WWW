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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

//import org.apache.hadoop.util.*;

public class TopKSearch {

        public static class Conv implements Comparable<Conv> {

			public double dist;
            public int id;

            public Conv(double d, int id) {
                this.dist = d;
                this.id = id;
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
        private String walkTable;
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
			// id, 위,경도
			int id = Integer.parseInt (arr[0]);
			int partId = id % numOfPartitions;

			emitkey.set(Integer.toString(partId));
			context.write(emitkey, value);
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
			// id, 위,경도
			int id = Integer.parseInt (arr[0]);
			int partId = id % numOfPartitions;

			emitkey.set(Integer.toString(partId));
			context.write(emitkey, value);
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
			numOfPartitions = conf.getInt ("numberOfPartitions", 2);
			K = conf.getInt ("K", 2);
			query =  conf.get ("queryPoint", "");
		}

		public void reduce(Text key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException
		{

			String[] keyarr = key.toString().split (",");
			double dist;
            PriorityQueue<Conv> queue = new PriorityQueue<Conv>();

			for(Text p : values){
				dist = dist(query, p.toString());

				if(dist>1000) continue; //거리가 1KM 초과 시 continue
                int id = Integer.parseInt(p.toString().split(",")[0]);

				Conv mt = new Conv(dist, id);
				if(queue.size() < K) queue.add(mt);
				else {
					if((queue.peek()).dist > dist){
						queue.remove();
						queue.add(mt);
					}
				}
			}


			while(!queue.isEmpty()){
				Conv mt = queue.poll();
				emitkey.set(Integer.toString(mt.id));
				emitval.set(Double.toString(mt.dist));
				context.write(emitkey, emitval);
			}

		}

		//위-경도 간 거리 구하기
	    public static double dist(String sp1, String sp2) {
             // parse string
            String[] strarr1 = sp1.split(",");
            String[] strarr2 = sp2.split(",");

			double lat1 = Double.parseDouble(strarr1[2]); 
			double lat2 = Double.parseDouble(strarr2[2]); 
			double lng1 = Double.parseDouble(strarr1[3); 
			double lng2 = Double.parseDouble(strarr2[3]); 
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

            // 각 클래스 지정
			job.setNumReduceTasks (2);
			job.setMapperClass(MapClass1.class);
			job.setReducerClass(ReduceClass1.class);

            // 출력 Key, Value타입 지정
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

            // 파일입력포맷와 파일출력포맷 지정
            MultipleInputs.addInputPath(job, new Path(otherArgs[1]));
			MultipleInputs.addInputPath(job, new Path(otherArgs[3]));
			FileOutputFormat.setOutputPath(job, output1);

             //하둡 분산 프로그램 실행
			if (! job.waitForCompletion(true))
			System.exit (1);

	}
}

