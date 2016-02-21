
#include "ImageMath.h"

float ImageMath::PI = 3.141592653589793;
float ImageMath::HALF_PI = ImageMath::PI/2.0f;
float ImageMath::QUARTER_PI = ImageMath::PI/4.0f;
float ImageMath::TWO_PI = ImageMath::PI * 2.0f;

float ImageMath::m00 = -0.5f;
float ImageMath::m01 = 1.5f;
float ImageMath::m02 = -1.5f;
float ImageMath::m03 = 0.5f;
float ImageMath::m10 = 1.0f;
float ImageMath::m11 = -2.5f;
float ImageMath::m12 = 2.0f;
float ImageMath::m13 = -0.5f;
float ImageMath::m20 = -0.5f;
float ImageMath::m21 = 0.0f;
float ImageMath::m22 = 0.5f;
float ImageMath::m23 = 0.0f;
float ImageMath::m30 = 0.0f;
float ImageMath::m31 = 1.0f;
float ImageMath::m32 = 0.0f;
float ImageMath::m33 = 0.0f;

ImageMath::ImageMath(void)
{
}


ImageMath::~ImageMath(void)
{
}
float ImageMath::clamp(float x, float a, float b)
{
	return x < a ? a : (x > b ? b : x);
}
float ImageMath::spline(float x, int numKnots, float * knots)
{
	int span;
	int numSpans = numKnots - 3;
	float k0, k1, k2, k3;
	float c0, c1, c2, c3;

	if(numSpans < 1)
	{
		throw "Too few knots in spline";
	}
	x = clamp(x, 0, 1) * numSpans;
	span = (int)x;
	if(span > numKnots - 4)
	{
		span = numKnots - 4;
	}
	x -= span;

	k0 = knots[span];
	k1 = knots[span + 1];
	k2 = knots[span + 2];
	k3 = knots[span + 3];

	c3 = m00 * k0 + m01 * k1 + m02 * k2 + m03 * k3;
	c2 = m10 * k0 + m11 * k1 + m12 * k2 + m13 * k3;
	c1 = m20 * k0 + m21 * k1 + m22 * k2 + m23 * k3;
	c0 = m30 * k0 + m31 * k1 + m32 * k2 + m33 * k3;

	return ((c3 * x + c2) * x + c1) * x + c0;
}
