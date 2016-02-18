package pe.phantasia;

import com.jabistudio.androidjhlabs.filter.BaseFilter;
import com.jabistudio.androidjhlabs.filter.BicubicScaleFilter;
import com.jabistudio.androidjhlabs.filter.BlockFilter;
import com.jabistudio.androidjhlabs.filter.CellularFilter;
import com.jabistudio.androidjhlabs.filter.ChannelMixFilter;
import com.jabistudio.androidjhlabs.filter.CircleFilter;
import com.jabistudio.androidjhlabs.filter.ColorHalftoneFilter;
import com.jabistudio.androidjhlabs.filter.ContourFilter;
import com.jabistudio.androidjhlabs.filter.ContrastFilter;
import com.jabistudio.androidjhlabs.filter.CrystallizeFilter;
import com.jabistudio.androidjhlabs.filter.Curve;
import com.jabistudio.androidjhlabs.filter.CurvesFilter;
import com.jabistudio.androidjhlabs.filter.DiffuseFilter;
import com.jabistudio.androidjhlabs.filter.DiffusionFilter;
import com.jabistudio.androidjhlabs.filter.DisplaceFilter;
import com.jabistudio.androidjhlabs.filter.DissolveFilter;
import com.jabistudio.androidjhlabs.filter.DoGFilter;
import com.jabistudio.androidjhlabs.filter.EmbossFilter;
import com.jabistudio.androidjhlabs.filter.ExposureFilter;
import com.jabistudio.androidjhlabs.filter.FieldWarpFilter;
import com.jabistudio.androidjhlabs.filter.GainFilter;
import com.jabistudio.androidjhlabs.filter.GammaFilter;
import com.jabistudio.androidjhlabs.filter.GlowFilter;
import com.jabistudio.androidjhlabs.filter.HSBAdjustFilter;
import com.jabistudio.androidjhlabs.filter.HalftoneFilter;
import com.jabistudio.androidjhlabs.filter.KaleidoscopeFilter;
import com.jabistudio.androidjhlabs.filter.MarbleFilter;
import com.jabistudio.androidjhlabs.filter.MotionBlurFilter;
import com.jabistudio.androidjhlabs.filter.OilFilter;
import com.jabistudio.androidjhlabs.filter.OpacityFilter;
import com.jabistudio.androidjhlabs.filter.PerspectiveFilter;
import com.jabistudio.androidjhlabs.filter.PinchFilter;
import com.jabistudio.androidjhlabs.filter.ShearFilter;
import com.jabistudio.androidjhlabs.filter.SphereFilter;
import com.jabistudio.androidjhlabs.filter.StampFilter;
import com.jabistudio.androidjhlabs.filter.SwimFilter;
import com.jabistudio.androidjhlabs.filter.TritoneFilter;
import com.jabistudio.androidjhlabs.filter.WaterFilter;
import com.jabistudio.androidjhlabs.filter.WeaveFilter;
import com.jabistudio.androidjhlabs.filter.util.AndroidUtils;

import android.drm.DrmStore.ConstraintsColumns;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.Bitmap.Config;

public class HanFilter extends Filter {
	
	public static final int HAN_BLOCK_FILTER = 1;
	public static final int HAN_CELLULAR_FILTER = 2;
	public static final int HAN_CHANNELMIX_FILTER = 3;
	public static final int HAN_CIRCLE_FILTER = 4;
	public static final int HAN_COLORHALFTONE_FILTER = 5;
	public static final int HAN_CONTOUR_FILTER = 6;
	public static final int HAN_CRYSTALLIZE_FILTER = 7;
	public static final int HAN_CURVES_FILTER = 8;
	public static final int HAN_DIFFUSE_FILTER = 9;
	public static final int HAN_DIFFUSION_FILTER = 10;
	public static final int HAN_DISPLACE_FILTER = 11;
	public static final int HAN_DOG_FILTER = 12;
	public static final int HAN_EMBOSS_FILTER = 13;
	public static final int HAN_EXPOSURE_FILTER = 14;
	public static final int HAN_FIELDWARP_FILTER = 15;
	public static final int HAN_GAIN_FILTER = 16;
	public static final int HAN_GAMMA_FILTER = 17;
	public static final int HAN_GLOW_FILTER = 18;
	public static final int HAN_HALFTONE_FILTER = 19;
	public static final int HAN_HSBADJUST_FILTER = 20;
	public static final int HAN_KLEIDOSTOPE_FILTER = 21;
	public static final int HAN_MARBLE_FILTER = 22;
	public static final int HAN_MOTIONBLUR_FILTER = 23;
	public static final int HAN_OIL_FILTER = 24;
	public static final int HAN_OPACITY_FILTER = 25;
	public static final int HAN_PERSPECTIVE_FILTER = 26;
	public static final int HAN_PINCH_FILTER = 27;
	public static final int HAN_SHEAR_FILTER = 28;
	public static final int HAN_SPHERE_FILTER = 29;
	public static final int HAN_STAMP_FILTER = 30;
	public static final int HAN_SWIM_FILTER = 31;
	public static final int HAN_TRITONE_FILTER = 32;
	public static final int HAN_WATER_FILTER = 33;
	public static final int HAN_WEAVE_FILTER = 34;
	
	public static final int HAN_CONTRAST_FILTER = 35;
	
	private int mFilterType = 1;
	
	public void setFilterType(int type){
		this.mFilterType = type;
	}
	
	public Bitmap transform(Bitmap image){
		BaseFilter filter = null;
		switch (mFilterType) {
		case HAN_BLOCK_FILTER:
			filter = new BlockFilter(20);
			break;
		case HAN_CELLULAR_FILTER:
			filter = new CellularFilter();
			break;
		case HAN_CHANNELMIX_FILTER:
			filter = new ChannelMixFilter();
			((ChannelMixFilter)filter).setIntoR(150);
			((ChannelMixFilter)filter).setIntoG(50);
			((ChannelMixFilter)filter).setIntoB(100);			
			break;
		case HAN_CIRCLE_FILTER:
			filter = new CircleFilter();
			break;
		case HAN_COLORHALFTONE_FILTER:
			filter = new ColorHalftoneFilter();
			break;
		case HAN_CONTOUR_FILTER:
			filter = new ContourFilter();
			break;
		case HAN_CONTRAST_FILTER:
			filter = new ContrastFilter();
			((ContrastFilter)filter).setBrightness(0.5f);
			break;			
		case HAN_CRYSTALLIZE_FILTER:
			filter = new CrystallizeFilter();
			break;
		case HAN_CURVES_FILTER:
			filter = new CurvesFilter();
	        Curve[] curves = new Curve[3];
	        Curve r = new Curve();
	        Curve g = new Curve();
	        Curve b = new Curve();
	
	        curves[0] = r;
	        curves[1] = g;
	        curves[2] = b;
	
	        r.y = new float[] {0f, 47.0f, 206.f, 255f};
	        r.x = new float[] {0f, 62f, 189f, 255f};
	
	        for(int i = 0 ;i<r.x.length;i++) {
	            r.x[i] = (r.x[i]*100)/255/100;
	            r.y[i] = (r.y[i]*100)/255/100;
	        }
	
	        g.y = new float[] {0f, 61f, 199f, 255f };
	        g.x = new float[] {0f, 75f, 187f, 255f };
	
	        for(int i = 0 ;i<g.x.length;i++) {
	            g.x[i] = (g.x[i]*100)/255/100;
	            g.y[i] = (g.y[i]*100)/255/100;
	        }
	
	        b.y = new float[] {0f, 66f, 191f, 255f};
	        b.x = new float[] {0f, 58f, 200f, 255f};
	
	        for(int i = 0 ;i<b.x.length;i++) {
	            b.x[i] = (b.x[i]*100)/255/100;
	            b.y[i] = (b.y[i]*100)/255/100;
	        }
	
	        ((CurvesFilter)filter).setCurves(curves);				
			break;
		case HAN_DIFFUSE_FILTER:
			filter = new DiffuseFilter();
			break;
		case HAN_DIFFUSION_FILTER:
			filter = new DiffusionFilter();
			break;
		case HAN_DISPLACE_FILTER:
			filter = new DisplaceFilter();
			break;
		case HAN_DOG_FILTER:
			filter = new DoGFilter();
			break;
		case HAN_EMBOSS_FILTER:
			filter = new EmbossFilter();
			break;
		case HAN_EXPOSURE_FILTER:
			filter = new ExposureFilter();
			break;
		case HAN_FIELDWARP_FILTER:
			filter = new FieldWarpFilter();
			break;
		case HAN_GAIN_FILTER:
			filter = new GainFilter();
			break;
		case HAN_GAMMA_FILTER:
			filter = new GammaFilter();
			((GammaFilter)filter).setGamma(0.5f);
			break;
		case HAN_GLOW_FILTER:
			filter = new GlowFilter();
			break;
		case HAN_HALFTONE_FILTER:
			filter = new HalftoneFilter();
			((HalftoneFilter)filter).setMonochrome(true);
			break;
		case HAN_HSBADJUST_FILTER:
			filter = new HSBAdjustFilter();
			((HSBAdjustFilter)filter).setSFactor(40f);		
			break;
		case HAN_KLEIDOSTOPE_FILTER:
			filter = new KaleidoscopeFilter();
			break;
		case HAN_MARBLE_FILTER:
			filter = new MarbleFilter();
			break;
		case HAN_MOTIONBLUR_FILTER:
			filter = new MotionBlurFilter();
			break;
		case HAN_OIL_FILTER:
			filter = new OilFilter();
			break;
		case HAN_OPACITY_FILTER:
			filter = new OpacityFilter();
			((OpacityFilter)filter).setOpacity(50);
			break;
		case HAN_PERSPECTIVE_FILTER:
			filter = new PerspectiveFilter();
			break;
		case HAN_PINCH_FILTER:
			filter = new PinchFilter();
			break;
		case HAN_SHEAR_FILTER:
			filter = new ShearFilter();
			break;
		case HAN_SPHERE_FILTER:
			filter = new SphereFilter();
			break;
		case HAN_STAMP_FILTER:
			filter = new StampFilter();
			break;
		case HAN_SWIM_FILTER:
			filter = new SwimFilter();
			break;
		case HAN_TRITONE_FILTER:
			filter = new TritoneFilter();
			break;
		case HAN_WATER_FILTER:
			filter = new WaterFilter();	
			break;
		case HAN_WEAVE_FILTER:
			filter = new WeaveFilter();
			break;			
		default:
			break;
		}
		

		
		mColors = AndroidUtils.bitmapToIntArray(image);
		width = image.getWidth();
		height = image.getHeight();		
		if(filter != null){
			mColors = filter.filter(mColors, width, height);
		}
		
		
		Bitmap destBitmap = Bitmap.createBitmap(mColors, 0, width, width, height, Config.ARGB_8888);
		return destBitmap;
	}

}
