#include "com_hanaone_ip_ImageFilter.h"
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

JNIEXPORT void JNICALL Java_com_hanaone_ip_ImageFilter_nativeCurveFilter
  (JNIEnv *jenv, jobject, jstring jinput, jstring joutput)
{
	const char * charInput = jenv->GetStringUTFChars(jinput, NULL);
	std::string input(charInput);
	const char * charOutput = jenv->GetStringUTFChars(joutput, NULL);
	std::string output(charOutput);

	cv::Mat image = cv::imread(input);

	CurvesFilter curvesFilter;
	Curve curves[3];

	Curve r;
	Curve g;
	Curve b;

	int length = 13;
	float * y = new float[length]{26.0f, 43.0f, 64.0f, 114.0f, 148.0f, 177.0f, 193.0f, 202.0f, 208.0f, 218.0f, 229.0f, 241.0f, 251.0f};
	float * x = new float[length]{0.0f, 15.0f, 32.0f, 65.0f, 83.0f, 109.0f, 127.0f, 146.0f, 170.0f, 195.0f, 215.0f, 234.0f, 255.0f };
	r.y = y;
	r.x = x;
	r.length = length;
    for(int i = 0 ;i<length;i++) {
        r.x[i] = (r.x[i]*100)/255/100;
        r.y[i] = (r.y[i]*100)/255/100;
    }

	y = new float[length]{0.0f, 32.0f, 72.0f, 123.0f, 147.0f, 188.0f, 205.0f, 210.0f, 222.0f, 224.0f, 235.0f, 246.0f, 255.0f };
	x = new float[length]{0.0f, 26.0f, 49.0f, 72.0f, 89.0f, 115.0f, 147.0f, 160.0f, 177.0f, 189.0f, 215.0f, 234.0f,255.0f};
	g.y = y;
	g.x = x;
	g.length = length;
    for(int i = 0 ;i<length;i++) {
        g.x[i] = (g.x[i]*100)/255/100;
        g.y[i] = (g.y[i]*100)/255/100;
    }

	y = new float[length]{29.0f, 72.0f, 124.0f, 147.0f, 162.0f, 175.0f, 184.0f, 189.0f, 195.0f, 203.0f, 216.0f, 237.0f, 247.0f};
	x = new float[length]{1.0f, 30.0f, 57.0f, 74.0f, 87.0f, 108.0f, 130.0f, 152.0f, 172.0f, 187.0f, 215.0f, 239.0f, 255.0f};
	b.y = y;
	b.x = x;
	b.length = length;
    for(int i = 0 ;i<length;i++) {
        b.x[i] = (b.x[i]*100)/255/100;
        b.y[i] = (b.y[i]*100)/255/100;
    }

    curves[0] = r;
    curves[1] = g;
    curves[2] = b;

    curvesFilter.setCurves(&curves[0], 3);


    cv::Mat out = curvesFilter.filter(image);

    //cv::imshow("", out);
    cv::imwrite(output, out);

    delete [] x;
    delete [] y;
}

