package bigdata.technical;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class SomeReducer extends Reducer<MotionKey, BooleanWritable, MotionKey, Text> {

	public void reduce(MotionKey key, Iterable<BooleanWritable> values, Context context) throws IOException, InterruptedException {
		
		boolean changed = false;

		int i = 0;
		for (BooleanWritable b : values) {
			if (i == 0) {
				changed = b.get();
			}

			if (i > 0 && b.get() == changed) {
				// Next state is the same as last, dont do anything
			} else {
				// State has changed, write new state
				changed = b.get();
				context.write(key, new Text(b.toString()));
			}
			i++;
		}
	}
}
