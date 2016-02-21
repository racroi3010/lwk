#pragma once
#include "BaseFilter.h"
class PointFilter :
	public BaseFilter
{
public:
	PointFilter();
	virtual ~PointFilter();

protected:
	cv::Mat filter(cv::Mat image);
	bool canFilterIndexColorModel;

};

