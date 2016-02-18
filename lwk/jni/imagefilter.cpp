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

