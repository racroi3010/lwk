#pragma once
#include "PixelUtils.h"
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
class BaseFilter
{
public:
	BaseFilter(void);
	~BaseFilter(void);
protected:
	virtual cv::Mat filter(cv::Mat image) = 0;
	virtual cv::Vec3b filterBGR(cv::Vec3b pixel) = 0;
};

