
#include "PixelUtils.h"

int PixelUtils::REPLACE = 0;
int PixelUtils::NORMAL = 1;
int PixelUtils::MIN = 2;
int PixelUtils::MAX = 3;
int PixelUtils::ADD = 4;
int PixelUtils::SUBTRACT = 5;
int PixelUtils::DIFFERENCE = 6;
int PixelUtils::MULTIPLY = 7;
int PixelUtils::HUE = 8;
int PixelUtils::SATURATION = 9;
int PixelUtils::VALUE = 10;

int PixelUtils::COLOR = 11;
int PixelUtils::SCREEN = 12;
int PixelUtils::AVERAGE = 13;
int PixelUtils::OVERLAY = 14;
int PixelUtils::CLEAR = 15;
int PixelUtils::EXCHANGE = 16;
int PixelUtils::DISSOLVE = 17;
int PixelUtils::DST_IN = 18;

int PixelUtils::ALPHA = 19;
int PixelUtils::ALPHA_TO_GRAY = 20;
PixelUtils::PixelUtils(void)
{
}


PixelUtils::~PixelUtils(void)
{
}
int PixelUtils::clamp(int c)
{
	return (c < 0 ? 0 : (c > 255 ? 255 : c));
}
