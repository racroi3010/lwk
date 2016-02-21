#pragma once
#include <stdio.h>
#include <iostream>
#include "ImageMath.h"
class Curve
{
public:
	Curve();
	Curve(Curve * curve, int length);
	~Curve();
	float * x;
	float * y;
	int length;

	int addKnot(float kx, float ky);
	void removeKnot(int n);
	void sortKnots();
	int * makeTable();
protected:
	
};

