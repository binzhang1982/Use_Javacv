package com.sh.zbin.video;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
 
import javax.imageio.ImageIO;
 
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;
 
public abstract class RandomFrameGrabberKit {
	// "C:\\Project\\workspace\\opencv-stitching\\VID_20170529_105049.mp4"
	private String filePath;
	// "./target"
	private String targerFilePath;
	// "img"
	private String targetFileName;
	// 10
	private int randomSize;
	
    public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTargerFilePath() {
		return targerFilePath;
	}

	public void setTargerFilePath(String targerFilePath) {
		this.targerFilePath = targerFilePath;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	public int getRandomSize() {
		return randomSize;
	}

	public void setRandomSize(int randomSize) {
		this.randomSize = randomSize;
	}
     
    public void run()
            throws Exception {
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(this.filePath);
        ff.start();
        int ffLength = ff.getLengthInFrames();
        List<Integer> randomGrab = random(ffLength, this.randomSize);
        int maxRandomGrab = randomGrab.get(randomGrab.size() - 1);
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabImage();
            if (randomGrab.contains(i)) {
                doExecuteFrame(f, i);
            }
            if (i >= maxRandomGrab) {
                break;
            }
            i++;
        }
        ff.stop();
    }
 
    private void doExecuteFrame(Frame f, int index) {
        if (null == f || null == f.image) {
            return;
        }
         
        Java2DFrameConverter converter = new Java2DFrameConverter();
 
        String imageMat = "jpg";
        String FileName = this.targerFilePath + File.separator + this.targetFileName + "_" + index + "." + imageMat;
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(FileName);
        try {
            ImageIO.write(bi, imageMat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static List<Integer> random(int baseNum, int length) {
 
        List<Integer> list = new ArrayList<Integer>(length);
        while (list.size() < length) {
            Integer next = (int) (Math.random() * baseNum);
            if (list.contains(next)) {
                continue;
            }
            list.add(next);
        }
        Collections.sort(list);
        return list;
    }
}