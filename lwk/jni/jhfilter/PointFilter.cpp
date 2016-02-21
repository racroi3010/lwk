
#include "PointFilter.h"


PointFilter::PointFilter()
{
	canFilterIndexColorModel = false;
}


PointFilter::~PointFilter()
{
}

cv::Mat PointFilter::filter(cv::Mat image)
{
	int x, y, i;
	int width = image.cols;
	int height = image.rows;

	cv::Mat outImage = cv::Mat::zeros(image.size(), image.type());

	cv::Vec3b pixel, filteredPixel;
	cv::Vec3b* rowBGR = 0, * rowBGRout = 0;
	for(y = 0; y < height; y ++)
	{
		rowBGR = image.ptr<cv::Vec3b>(y);
		rowBGRout = outImage.ptr<cv::Vec3b>(y);
		for(x = 0; x < width; x ++)
		{
			pixel = rowBGR[x];

			filteredPixel = filterBGR(pixel);

			for(i = 0; i < 3; i ++)
			{
				rowBGRout[x][i] = filteredPixel[i];

			}
		}

	}
	return outImage;

}
