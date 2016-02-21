#include <cstdio>
#include <cstdlib>
#include <cstring>
#include "Curve.h"


Curve::Curve()
{
	length = 2;
	x = new float[length];
	y = new float[length];

	x[0] = y[0] = 0;
	x[1] = y[1] = 1;

}
Curve::Curve(Curve * curve, int length)
{
	this->length = length;
	std::memcpy(x, curve->x, length * sizeof(float));
	std::memcpy(y, curve->y, length * sizeof(float));

}

Curve::~Curve()
{
	//delete [] x;	
	//delete [] y;
}
int Curve::addKnot(float kx, float ky)
{
	int pos = -1;
	int numKnots = this->length;
	float * nx = new float[numKnots + 1];
	float * ny = new float[numKnots + 1];

	int j = 0;
	for(int i = 0; i < numKnots; i ++)
	{
		if(pos == -1 && x[i] > kx)
		{
			pos = j;
			nx[j] = kx;
			ny[j] = ky;
			j ++;
		}

		nx[j] = x[i];
		ny[j] = y[i];
		j ++;
	}
	if(pos == -1)
	{
		pos = j;
		nx[j] = kx;
		ny[j] = ky;
	}
	x = nx;
	y = ny;
	this->length ++;

	delete [] nx;
	delete [] ny;

	return pos;
}
void Curve::removeKnot(int n)
{
	int numKnots = this->length;
	if(numKnots <= 2)
	{
		return;
	}
	float * nx = new float[numKnots - 1];
	float * ny = new float[numKnots - 1];
	int j = 0;
	for(int i = 0; i < numKnots - 1; i ++)
	{
		if(i == n)
			j ++;
		nx[i] = x[j];
		ny[i] = y[j];
		j ++;
	}
	x = nx;
	y = ny;
	this->length --;

	delete [] nx;
	delete [] ny;
}
void Curve::sortKnots()
{
	int numKnots = this->length;
	for(int i = 1; i < numKnots - 1; i ++)
		for(int j = 1; j < i; j ++)
		if(x[i] < x[j])	
		{
			float t = x[i];
			x[i] = x[j];
			x[j] = t;
			t = y[i];
			y[i] = y[j];
			y[j] = t;
		}

}
int * Curve::makeTable()
{
	int numKnots = this->length;
	float * nx = new float[numKnots + 2];
	float * ny = new float[numKnots + 2];

	for(int i = 0; i < numKnots; i ++)
	{
		nx[i + 1] = x[i];
		ny[i + 1] = y[i];
	}

	nx[0] = nx[1];
	ny[0] = ny[1];

	nx[numKnots + 1] = nx[numKnots];
	ny[numKnots + 1] = ny[numKnots];

	int * table = new int[256];
	for(int i = 0; i < 1024; i ++)
	{
		float f = i/1024.0f;
		int x = (int)(255 * ImageMath::spline(f, numKnots + 2, nx) + 0.5f);
		int y = (int)(255 * ImageMath::spline(f, numKnots + 2, ny) + 0.5f);

		x = ImageMath::clamp(x, 0, 255);
		y = ImageMath::clamp(y, 0, 255);

		table[x] = y;
		
	}
	// wroongggg
	for(int i = 0; i < 256; i ++)
	{
		std::cout << table[i] << ", ";
	}

	delete [] nx;
	delete [] ny;

	return table;
}
