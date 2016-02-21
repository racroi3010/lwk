#pragma once
class ImageMath
{
public:
	ImageMath(void);
	~ImageMath(void);

	static float PI;
	static float HALF_PI;
	static float QUARTER_PI;
	static float TWO_PI;

	static float m00;
	static float m01;
	static float m02;
	static float m03;
	static float m10;
	static float m11;
	static float m12;
	static float m13;
	static float m20;
	static float m21;
	static float m22;
	static float m23;
	static float m30;
	static float m31;
	static float m32;
	static float m33;


	static float spline(float x, int numKnots, float * knots);
	static float clamp(float x, float a, float b);
};

