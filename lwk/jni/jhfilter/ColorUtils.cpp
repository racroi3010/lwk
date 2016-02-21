/*
 * ColorUtils.cpp
 *
 *  Created on: Feb 19, 2016
 *      Author: my
 */

#include "ColorUtils.h"

ColorUtils::ColorUtils() {
	// TODO Auto-generated constructor stub

}

ColorUtils::~ColorUtils() {
	// TODO Auto-generated destructor stub
}
cv::Vec3b ColorUtils::RGB2HSV(cv::Vec3b rgb)
{
	float r = (float)rgb[0]/255.0f;
	float g = (float)rgb[1]/255.0f;
	float b = (float)rgb[2]/255.0f;

	float max = std::max(r, std::max(g, b));
	float min = std::min(r, std::min(g, b));

	float V = max;
	float S = 0.0f;
	if(V != 0){
		S = (V - min)/V;
	}

	float H = 0.0f;

	if(V == r){
		H = 60.0f * (g - b)/(V - min);
	} else if(V == g){
		H = 120.0f + 60.0f * (b - r)/(V - min);
	} else if(V == b){
		H = 240.0f + 60.0f * (r - g)/(V - min);
	}

	H = H < 0.0f ? H + 360.0f : H;

	V *= 255.0f;
	S *= 255.0f;
	H /= 2.0f;

	return cv::Vec3b(H, S, V);
}
cv::Vec3b ColorUtils::HSV2RGB(cv::Vec3b hsv)
{
	float H = hsv[0] * 2;
	float S = (float)hsv[1]/255.0f;
	float V = (float)hsv[2]/255.0f;

	float C = V * S;
	float X = C * (1 - (float)std::abs(((int)H/60)%2 - 1.0f));
	float m = V - C;

	float R = 0.0f;
	float G = 0.0f;
	float B = 0.0f;
	if(H >=0.0f && H < 60.0f){
		R = C;
		G = X;
		B = 0;
	} else if(H >= 60.0f && H < 120.0f){
		R = X;
		G = C;
		B = 0;
	} else if(H >= 120.0f && H < 180.0f){
		R = 0;
		G = C;
		B = X;
	} else if(H >= 180.0f && H < 240.0f){
		R = 0;
		G = X;
		B = C;
	} else if(H >= 240.0f && H < 300.0f){
		R = X;
		G = 0;
		B = C;
	} else if(H >= 300.0f && H < 360.0f){
		R = C;
		G = 0;
		B = X;
	}

	R = (R + m) * 255.0f;
	G = (G + m) * 255.0f;
	G = (G + m) * 255.0f;

	return cv::Vec3b(R, G, B);
}
cv::Vec3b ColorUtils::BGR2HSV(cv::Vec3b bgr)
{
	float r = (float)bgr[2]/255.0f;
	float g = (float)bgr[1]/255.0f;
	float b = (float)bgr[0]/255.0f;

	float max = std::max(r, std::max(g, b));
	float min = std::min(r, std::min(g, b));

	float V = max;
	float S = 0.0f;
	if(V != 0){
		S = (V - min)/V;
	}

	float H = 0.0f;

	if(V == r){
		H = 60.0f * (g - b)/(V - min);
	} else if(V == g){
		H = 120.0f + 60.0f * (b - r)/(V - min);
	} else if(V == b){
		H = 240.0f + 60.0f * (r - g)/(V - min);
	}

	H = H < 0.0f ? H + 360.0f : H;

	V *= 255.0f;
	S *= 255.0f;
	H /= 2.0f;

	return cv::Vec3b(H, S, V);
}
cv::Vec3b ColorUtils::HSV2BGR(cv::Vec3b hsv)
{
	float H = hsv[0] * 2;
	float S = (float)hsv[1]/255.0f;
	float V = (float)hsv[2]/255.0f;

	float C = V * S;
	float X = C * (1 - (float)std::abs(((int)H/60)%2 - 1));
	float m = V - C;

	float R = 0.0f;
	float G = 0.0f;
	float B = 0.0f;
	if(H >=0.0f && H < 60.0f){
		R = C;
		G = X;
		B = 0;
	} else if(H >= 60.0f && H < 120.0f){
		R = X;
		G = C;
		B = 0;
	} else if(H >= 120.0f && H < 180.0f){
		R = 0;
		G = C;
		B = X;
	} else if(H >= 180.0f && H < 240.0f){
		R = 0;
		G = X;
		B = C;
	} else if(H >= 240.0f && H < 300.0f){
		R = X;
		G = 0;
		B = C;
	} else if(H >= 300.0f && H < 360.0f){
		R = C;
		G = 0;
		B = X;
	}

	R = (R + m) * 255.0f;
	G = (G + m) * 255.0f;
	B = (B + m) * 255.0f;

	return cv::Vec3b(B, G, R);
}
