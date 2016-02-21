
#include "CurvesFilter.h"


CurvesFilter::CurvesFilter()
{
	length = 1;
	curves = new Curve[length];

}


CurvesFilter::~CurvesFilter()
{
	delete [] curves;
}
void CurvesFilter::initialize()
{
	initialized = true;
	if(this->length == 1)
	{
		rTable = gTable = bTable = curves[0].makeTable();
	} 
	else 
	{
		rTable = curves[0].makeTable();
		gTable = curves[1].makeTable();
		bTable = curves[2].makeTable();
	}
}

void CurvesFilter::setCurve(Curve curve)
{
	curves = new Curve[this->length];
	curves[0] = curve;
}
void CurvesFilter::setCurves(Curve * curves, int length)
{
	if(curves == NULL)
	{
		throw "Curves must be not NULL";
	}
	if(length != 1 && length != 3)
	{
		throw "Curves must be length 1 or 3";
	}
	this->length = length;
	this->curves = new Curve[length];
	memcpy(this->curves, curves, length * sizeof(Curve));
	this->curves->length = curves->length;
	initialized = false;

}
Curve * CurvesFilter::getCurves()
{
	return this->curves;
}
cv::Mat CurvesFilter::filter(cv::Mat image)
{
	if(!initialized)
	{
		initialize();
	}
	return TransferFilter::filter(image);
}
