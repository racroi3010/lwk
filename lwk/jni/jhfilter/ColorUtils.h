/*
 * ColorUtils.h
 *
 *  Created on: Feb 19, 2016
 *      Author: my
 */

#ifndef COLORUTILS_H_
#define COLORUTILS_H_
#include <opencv2/core/core.hpp>

class ColorUtils {
public:
	ColorUtils();
	virtual ~ColorUtils();

	static cv::Vec3b RGB2HSV(cv::Vec3b rgb);
	static cv::Vec3b HSV2RGB(cv::Vec3b hsv);

	static cv::Vec3b BGR2HSV(cv::Vec3b bgr);
	static cv::Vec3b HSV2BGR(cv::Vec3b hsv);

};

#endif /* COLORUTILS_H_ */
