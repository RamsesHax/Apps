package com.jfacedetection;

import org.opencv.core.Point;

import java.awt.Color;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;


public class JFaceDetection {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//-------adaugam imaginea
		String sourceImg = "images/Rares si Daria.jpeg";		
		Mat imaginea = Imgcodecs.imread(sourceImg);				
		//-------adaugam modelul unei fete pentru a putea face detectia (format XML)
		String xmlFile = "xml/lbpcascade_frontalface_improved.xml";
		CascadeClassifier cc = new CascadeClassifier(xmlFile);
		//-------facem detectia
		MatOfRect faceDetection = new MatOfRect();
		cc.detectMultiScale(imaginea, faceDetection);
		
		System.out.println("Fete detectate: " + faceDetection.toArray().length);
		
		for(Rect rect : faceDetection.toArray()) {
			Imgproc.rectangle(imaginea, rect, new Scalar(0,0,255));
		}
		
		Imgcodecs.imwrite(sourceImg, imaginea);
		System.out.println("Done");
	}

}
