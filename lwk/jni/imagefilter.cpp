#include "com_hanaone_ip_ImageFilter.h"
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

	cv::Vec3b getTemp2RGB(jint temporature)
	{
		temporature /= 100;
		cv::Vec3b pixel = cv::Vec3b(0, 0, 0);
		// red
		if(temporature <= 66)
		{
			pixel[0] = 255;
		}
		else
		{
			pixel[0] = temporature - 60;
			pixel[0] = 329.698727446 * (pow((double)pixel[0], -0.1332047592));
			pixel[0] = pixel[0] > 255 ? 255 : (pixel[0] < 0 ? 0 : pixel[0]);
		}

		// green
		if(temporature <= 66)
		{
			pixel[1] = temporature;
			pixel[1] = 99.4708025861 * log(pixel[1]) - 161.1195681661;
			pixel[1] = pixel[1] > 255 ? 255 : (pixel[1] < 0 ? 0 : pixel[1]);

		}
		else
		{
			pixel[1] = temporature - 60;
			pixel[1] = 288.1221695283 * (pow((double)pixel[1], -0.0755148492));
			pixel[1] = pixel[1] > 255 ? 255 : (pixel[1] < 0 ? 0 : pixel[1]);
		}

		// blue
		if(temporature >= 66)
		{
			pixel[2] = 255;
		}
		else
		{
			if(temporature <= 19)
			{
				pixel[2] = 0;
			}
			else
			{
				pixel[2] = temporature - 10;
				pixel[2] = 138.5177312231 * log(pixel[0]) - 305.0447927307;
				pixel[2] = pixel[2] > 255 ? 255 : (pixel[2] < 0 ? 0 : pixel[2]);
			}
		}
		return pixel;
	}
	void RGB2HSL(float R, float G, float B, float &H, float &S, float &L)
	{
		float tR = R/255;
		float tG = G/255;
		float tB = B/255;

		float cMax = std::max(tR, std::max(tG, tB));
		float cMin = std::min(tR, std::min(tG, tB));
		float delta = cMax - cMin;

		if(delta == 0)
		{
			H = 0.0f;
		}
		else if(cMax == tR)
		{
			H = 60.0f * ((tG - tB)/delta + (tG < tB ? 6.0f : 0.0f));
		}
		else if(cMax == tG)
		{
			H = 60.0f * (((tB - tR)/delta) + 2.0f);
		}
		else
		{
			H = 60.0f * (((tR - tG)/delta) + 4.0f);
		}

		L = (cMax + cMin)/2.0f;

		if(L == 0)
		{
			S = 0.0f;
		}
		else
		{
			S = delta/(1 - abs(2.0f * L - 1));
		}
	}
	void HSL2RGB(float H, float S, float L, float& R, float& G, float& B)
	{
		float C = (1.0f - abs(2.0f * L - 1.0f)) * S;
		float X = C * (1 - abs(fmod(H/60.0f, 2) - 1));
		float m = (L - C/2) * 255.0f;

		if(H >= 0 && H < 60)
		{
			R = C * 255;
			G = X * 255;
			B = 0;
		}
		else if(H < 120)
		{
			R = X * 255;
			G = C * 255;
			B = 0;
		}
		else if(H < 180)
		{
			R = 0;
			G = C * 255;
			B = X * 255;
		}
		else if(H < 240)
		{
			R = 0;
			G = X * 255;
			B = C * 255;
		}
		else if(H < 300)
		{
			R = X * 255;
			G = 0;
			B = C * 255;
		}
		else if(H < 360)
		{
			R = C * 255;
			G = 0;
			B = X * 255;
		}
		R = R + m;
		//R = R > 255 ? 255 : (R < 0 ? 0 : R);

		G = G + m;
		//G = G > 255 ? 255 : (G < 0 ? 0 : G);

		B = B + m;
		//B = B > 255 ? 255 : (B < 0 ? 0 : B);
	}


JNIEXPORT void JNICALL Java_com_hanaone_ip_ImageFilter_hsbAdFilter
  (JNIEnv *jenv, jobject, jstring jinput, jstring joutput)
{
	const char * charInput = jenv->GetStringUTFChars(jinput, NULL);
	std::string input(charInput);
	const char * charOutput = jenv->GetStringUTFChars(joutput, NULL);
	std::string output(charOutput);

	cv::Mat matInput = cv::imread(input);
	cv::Mat matOutput;
	if(matInput.data){
		cv::cvtColor(matInput, matOutput, CV_BGR2HSV);

		cv::imwrite(output, matOutput);
	}
}
JNIEXPORT void JNICALL Java_com_hanaone_ip_ImageFilter_WhiteBalance
  (JNIEnv *jenv, jobject, jstring jinput, jstring joutput, jfloat temporature, jfloat tempStrength)
{
	const char * charInput = jenv->GetStringUTFChars(jinput, NULL);
	std::string input(charInput);
	const char * charOutput = jenv->GetStringUTFChars(joutput, NULL);
	std::string output(charOutput);

	cv::Mat matInput = cv::imread(input);


	int x, y;
	int width = matInput.cols;
	int height = matInput.rows;

	tempStrength = tempStrength/100;

	cv::Vec3b tempRGB = getTemp2RGB(temporature);

	tempRGB[0] *= tempStrength;
	tempRGB[1] *= tempStrength;
	tempRGB[2] *= tempStrength;
	float iS = 1 - tempStrength;

	float lumin = 0;
	float R = 0.0f, G = 0.0f, B = 0.0f;
	float H = 0.0f, S = 0.0f, L = 0.0f;
	//cv::Mat matOutput = cv::Mat:zeros(height, width, cv::CV_8UC3);
	cv::Vec3b pixel;
	for(y = 0; y < height; y++)
	{
		cv::Vec3b* rowBGR = matInput.ptr<cv::Vec3b>(y);
		for(x = 0; x < width; x ++)
		{

			pixel = rowBGR[x];

			// get lumination
			lumin = ((float)(std::max(pixel[0], std::max(pixel[1],  pixel[2])) + std::min(pixel[0], std::min(pixel[1], pixel[2]))))/ (2.0f * 255.0f);

			// blend color
			R = pixel[0] * iS + tempRGB[0];
			G = pixel[1] * iS + tempRGB[1];
			B = pixel[2] * iS + tempRGB[2];

			// convert RGB to HSL
			RGB2HSL(R, G, B, H, S, L);

			// convert HSL to RGB
			HSL2RGB(H, S, lumin, R, G, B);


			pixel[0]= R;
			pixel[1]= G;
			pixel[2]= B;


			//inImage.at<Vec4b>(y, x) = pixel4B;
			rowBGR[x] = pixel;
		}
	}

	cv::imwrite(output, matInput);


}
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

