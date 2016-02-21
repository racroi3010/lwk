#pragma once
class PixelUtils
{
public:
	PixelUtils(void);
	~PixelUtils(void);
	static int REPLACE;
	static int NORMAL;
	static int MIN;
	static int MAX;
	static int ADD;
	static int SUBTRACT;
	static int DIFFERENCE;
	static int MULTIPLY;
	static int HUE;
	static int SATURATION;
	static int VALUE;

	static int COLOR;
	static int SCREEN;
	static int AVERAGE;
	static int OVERLAY;
	static int CLEAR;
	static int EXCHANGE;
	static int DISSOLVE;
	static int DST_IN;

	static int ALPHA;
	static int ALPHA_TO_GRAY;

	static int clamp(int c);
};

