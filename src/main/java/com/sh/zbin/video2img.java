package com.sh.zbin;

import java.util.List;

import com.sh.zbin.video.CustomFrameGrabberKit;
import com.sh.zbin.video.Stitching;

public class video2img {

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("java.library.path"));
		CustomFrameGrabberKit kit = new CustomFrameGrabberKit();
		kit.setFilePath("C:\\Project\\workspace\\opencv-stitching\\VID_20170529_175241.mp4");
		kit.setInterval(1);
		List<String> paths = kit.run();
		Stitching sit = new Stitching();
		sit.setTry_use_gpu(true);
		sit.run(paths);
	}

}
