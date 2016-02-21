#pragma once
#include "TransferFilter.h"
#include "Curve.h"

class CurvesFilter :
	public TransferFilter
{
public:
	CurvesFilter();
	~CurvesFilter();


	void setCurve(Curve curve);
	void setCurves(Curve * curve, int length);
	Curve * getCurves();
	cv::Mat filter(cv::Mat image);

private:
	Curve * curves;
	int length;
protected:
	void initialize();


};

