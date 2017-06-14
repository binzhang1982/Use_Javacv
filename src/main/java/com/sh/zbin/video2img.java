package com.sh.zbin;

import java.util.ArrayList;
import java.util.List;

import com.sh.zbin.video.CustomFrameGrabberKit;
import com.sh.zbin.video.Stitching;

public class video2img {

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("java.library.path"));
		CustomFrameGrabberKit kit = new CustomFrameGrabberKit();
		kit.setFilePath("C:\\Project\\workspace\\opencv-stitching\\street4.mp4");
		kit.setInterval(1);
		List<String> paths = kit.run();
		Stitching sit = new Stitching();
		sit.setTry_use_gpu(true);
		List<String> run_paths = new ArrayList<String>();
		int runCnt = 30;
		int resultCnt = 1;
		for (int cnt = 0; cnt < paths.size(); cnt++) {
			if (run_paths.size() == runCnt) {
				sit.setResult_name("C:\\" + resultCnt + "_result.jpg");
				sit.run(run_paths);
				run_paths.removeAll(run_paths);
				resultCnt ++;
				break;
			} else {
				run_paths.add(paths.get(cnt));
			}
			if (cnt == paths.size() - 1) {
				sit.setResult_name("C:\\" + resultCnt + "_result.jpg");
				sit.run(run_paths);
				run_paths.removeAll(run_paths);
			}
		}
		

//		Stitching sit = new Stitching();
//		sit.setTry_use_gpu(true);
//		List<String> run_paths = new ArrayList<String>();
//		for (int i=1; i<=18; i++) {
//			run_paths.add("cut_img\\img"+ i +".jpg");
//		}
//		sit.run(run_paths);
	}

}
