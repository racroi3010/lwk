#pragma once
#include "PointFilter.h"
class TransferFilter :
	public PointFilter
{
public:
	TransferFilter();
	~TransferFilter();

protected:
	int * rTable, * gTable, * bTable;
	bool initialized;

	cv::Vec3b filterBGR(cv::Vec3b pixel);
	cv::Mat filter(cv::Mat image);

	void initialize();
	int * makeTable();
	float transferFunction(float v);
};

