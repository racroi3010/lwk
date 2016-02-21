
#include "TransferFilter.h"


TransferFilter::TransferFilter()
{
	initialized = false;

}


TransferFilter::~TransferFilter()
{
	delete [] rTable;
	delete [] gTable;
	delete [] bTable;
}

cv::Vec3b TransferFilter::filterBGR(cv::Vec3b pixel)
{
	int r = pixel[2];
	int g = pixel[1];
	int b = pixel[0];

	r = rTable[r];
	g = gTable[g];
	b = bTable[b];

	return cv::Vec3b(b, g, r);
}
cv::Mat TransferFilter::filter(cv::Mat image)
{
	if(!initialized)
	{
		initialize();
	}
	return PointFilter::filter(image);
}
void TransferFilter::initialize()
{
	initialized = true;
	rTable = gTable = bTable = makeTable();
}
int * TransferFilter::makeTable()
{
	int * table = new int[256];
	for(int i = 0; i < 256; i ++)
	{
		table[i] = PixelUtils::clamp((int)( 255 * transferFunction(i/255.0f)));
	}
	return table;
}
float TransferFilter::transferFunction(float v)
{
	return 0;
}
