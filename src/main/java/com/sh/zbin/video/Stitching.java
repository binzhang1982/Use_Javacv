package com.sh.zbin.video;
/*M///////////////////////////////////////////////////////////////////////////////////////
//
// IMPORTANT: READ BEFORE DOWNLOADING, COPYING, INSTALLING OR USING.
//
// By downloading, copying, installing or using the software you agree to this license.
// If you do not agree to this license, do not download, install,
// copy or use the software.
//
//
// License Agreement
// For Open Source Computer Vision Library
//
// Copyright (C) 2000-2008, Intel Corporation, all rights reserved.
// Copyright (C) 2009, Willow Garage Inc., all rights reserved.
// Third party copyrights are property of their respective owners.
//
// Redistribution and use in source and binary forms, with or without modification,
// are permitted provided that the following conditions are met:
//
// * Redistribution's of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// * Redistribution's in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
//
// * The name of the copyright holders may not be used to endorse or promote products
// derived from this software without specific prior written permission.
//
// This software is provided by the copyright holders and contributors "as is" and
// any express or implied warranties, including, but not limited to, the implied
// warranties of merchantability and fitness for a particular purpose are disclaimed.
// In no event shall the Intel Corporation or contributors be liable for any direct,
// indirect, incidental, special, exemplary, or consequential damages
// (including, but not limited to, procurement of substitute goods or services;
// loss of use, data, or profits; or business interruption) however caused
// and on any theory of liability, whether in contract, strict liability,
// or tort (including negligence or otherwise) arising in any way out of
// the use of this software, even if advised of the possibility of such damage.
//
//M*/

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_stitching.Stitcher;

import java.util.Date;
import java.util.List;

public class Stitching {
    private boolean try_use_gpu = false;
    private MatVector imgs = new MatVector();
    private String result_name = "C:\\result.jpg";

    public boolean isTry_use_gpu() {
		return try_use_gpu;
	}

	public void setTry_use_gpu(boolean try_use_gpu) {
		this.try_use_gpu = try_use_gpu;
	}

	public MatVector getImgs() {
		return imgs;
	}

	public void setImgs(MatVector imgs) {
		this.imgs = imgs;
	}

	public String getResult_name() {
		return result_name;
	}

	public void setResult_name(String result_name) {
		this.result_name = result_name;
	}

	public void run(List<String> imgPaths) {
    	System.out.println("Stitching run start : " + new Date());
//        int retval = parseCmdArgs(args);
//        if (retval != 0) {
//            System.exit(-1);
//        }

    	for (String imgPath : imgPaths) {
    		Mat img = imread(imgPath);
    		if (img.empty()) {
    			System.out.println("Can't read image '" + imgPath + "'");
    			return;
    		}
    		this.imgs.resize(imgs.size() + 1);
    		imgs.put(imgs.size() - 1, img);
    	}
    	
        Mat pano = new Mat();
        Stitcher stitcher = Stitcher.createDefault(this.try_use_gpu);

    	System.out.println("stitcher start : " + new Date());
        int status = stitcher.stitch(this.imgs, pano);
    	System.out.println("stitcher end : " + new Date());

        if (status != Stitcher.OK) {
            System.out.println("Can't stitch images, error code = " + status);
            System.exit(-1);
        }

    	System.out.println("imwrite start : " + new Date());
    	System.out.println("pano's size :" + pano.arraySize());
        imwrite(this.result_name, pano);
    	System.out.println("imwrite end : " + new Date());

        System.out.println("Images stitched together to make " + this.result_name);
    	System.out.println("Stitching run end : " + new Date());
	}
	
//    static void printUsage() {
//        System.out.println(
//            "Rotation model images stitcher.\n\n"
//          + "stitching img1 img2 [...imgN]\n\n"
//          + "Flags:\n"
//          + "  --try_use_gpu (yes|no)\n"
//          + "      Try to use GPU. The default value is 'no'. All default values\n"
//          + "      are for CPU mode.\n"
//          + "  --output <result_img>\n"
//          + "      The default is 'result.jpg'.");
//    }

//    static int parseCmdArgs() {
//        if (args.length == 0) {
//            printUsage();
//            return -1;
//        }
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].equals("--help") || args.equals("/?")) {
//                printUsage();
//                return -1;
//            } else if (args[i].equals("--try_use_gpu")) {
//                if (args[i + 1].equals("no")) {
//                    try_use_gpu = false;
//                } else if (args[i + 1].equals("yes")) {
//                    try_use_gpu = true;
//                } else {
//                    System.out.println("Bad --try_use_gpu flag value");
//                    return -1;
//                }
//                i++;
//            } else if (args[i].equals("--output")) {
//                result_name = args[i + 1];
//                i++;
//            } else {
//                Mat img = imread(args[i]);
//                if (img.empty()) {
//                    System.out.println("Can't read image '" + args[i] + "'");
//                    return -1;
//                }
//                imgs.resize(imgs.size() + 1);
//                imgs.put(imgs.size() - 1, img);
//            }
//        }
//        return 0;
//    }
}