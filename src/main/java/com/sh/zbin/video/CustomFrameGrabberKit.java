package com.sh.zbin.video;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import org.bytedeco.javacpp.opencv_videoio;
import org.bytedeco.javacpp.opencv_videoio.CvCapture;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.*;

public class CustomFrameGrabberKit {

	// "C:\\Project\\workspace\\opencv-stitching\\VID_20170601_180323.mp4"
	private String filePath;
	
	// 1s
	private int interval;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
	
//	public static void main(String[] args) {
//		System.out.println(System.getProperty("java.library.path"));
//		
////		 System.out.println("Welcome to OpenCV " + Core.VERSION);
////		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
////		 Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
////		 System.out.println("m = " + m.dump());
//
//		// 加载本地的OpenCV库，这样就可以用它来调用Java API
////		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		CustomFrameGrabberKit t = new CustomFrameGrabberKit();
////		t.test();
////		t.run();
////		t.run2();
//		t.run();
////		System.out.println(t.CmpPic("d:/img/219.jpg"));
//	}
	
	public List<String> run() throws Exception {
//		System.out.println("CustomFrameGrabberKit run start : " + new Date());
//		CvCapture capture = opencv_videoio.cvCreateFileCapture(this.filePath);
//		
//		//帧率
//		int fps = (int) opencv_videoio.cvGetCaptureProperty(capture, opencv_videoio.CV_CAP_PROP_FPS);
//		System.out.println("帧率:" + fps);
//		
//		IplImage frame = null;
//		double pos1 = 0;
//		
//		int rootCount = 0;
//		int imgCount = 1;
//		List<String> imgPaths = new ArrayList<String>();
//		
//		while (true) {
//			
//			//读取关键帧
//			frame = opencv_videoio.cvQueryFrame(capture);
//			
//			rootCount = fps * this.interval;
//			while(rootCount > 0 ){
//				//这一段的目的是跳过间隔的帧数,也就是说fps是帧率(一秒钟有多少帧),在读取间隔帧后,跳过fps数量的间隔倍的帧就相当于跳过了间隔秒。
//				frame = opencv_videoio.cvQueryFrame(capture);
//				rootCount--;
//			}
//
//			//获取当前帧的位置
//			pos1 = opencv_videoio.cvGetCaptureProperty(capture,opencv_videoio.CV_CAP_PROP_POS_FRAMES);
//			System.out.println("当前帧:" + pos1);
//
//			if (null == frame)
//				break;
//			
//			cvSaveImage("cut_img/img" + imgCount + ".jpg", frame);
//			imgPaths.add("cut_img\\img" + imgCount + ".jpg");
//			imgCount += 1;
//		}
//		
//		opencv_videoio.cvReleaseCapture(capture);
//		System.out.println("CustomFrameGrabberKit run end : " + new Date());
//		return imgPaths;
		
		System.out.println("CustomFrameGrabberKit run start : " + new Date());
		FrameGrabber grabber = FFmpegFrameGrabber.createDefault(this.filePath);
		OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
		grabber.start();
		System.out.println("FPS : " + (int)grabber.getFrameRate());
		Frame grabbedFrame;
		Float scale = 0.5f;
		int secCnt = 1;
		int imgCount = 1;
		List<String> imgPaths = new ArrayList<String>();
		while ((grabbedFrame = grabber.grabFrame()) != null) {// .isVisible()
			if (grabbedFrame.keyFrame && grabbedFrame.image != null) {
				if ((secCnt - 1) % this.interval == 0 ) {
					Mat pano = converterToMat.convert(grabbedFrame);
//					Mat resizePano = new Mat();
//					Mat resizeGrayPano = new Mat();
//					改像素
//					Size sz = new Size((int) (pano.size().width()/2), (int)(pano.size().height()/2));
//					opencv_imgproc.resize(pano, resizePano, sz);
//					改黑白
//					opencv_imgproc.cvtColor(resizePano, resizeGrayPano, opencv_imgproc.COLOR_RGB2GRAY);
					System.out.println("cut_img/img" + imgCount + ".jpg");
					imwrite("cut_img/img" + imgCount + ".jpg", pano);
					imgPaths.add("cut_img\\img" + imgCount + ".jpg");
					imgCount ++;
				}
				secCnt ++;
			}
			if (grabbedFrame.image == null) {
				continue;
			}
		}
        grabber.restart();
        grabber.stop();
        grabber.release();

		System.out.println("CustomFrameGrabberKit run end : " + new Date());
		return imgPaths;
	}
}
