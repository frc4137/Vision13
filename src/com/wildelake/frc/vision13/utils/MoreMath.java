package com.wildelake.frc.vision13.utils;

public class MoreMath {

	public static double atan(double x) {
		boolean signChange=false;
		boolean Invert=false;
		int sp=0;
		double x2, a;
		// check up the sign change
		if(x<0.)
		{
			x=-x;
			signChange=true;
		}
		// check up the invertation
		if(x>1.)
		{
			x=1/x;
			Invert=true;
		}
		// process shrinking the domain until x<PI/12
		while(x>Math.PI/12)
		{
			sp++;
			a=x+1.732050808;
			a=1/a;
			x=x*1.732050808;
			x=x-1;
			x=x*a;
		}
		// calculation core
		x2=x*x;
		a=x2+1.4087812;
		a=0.55913709/a;
		a=a+0.60310579;
		a=a-(x2*0.05160454);
		a=a*x;
		// process until sp=0
		while(sp>0)
		{
			a=a+Math.PI/6;
			sp--;
		}
		// invertation took place
		if(Invert) a=Math.PI/2-a;
		// sign change took place
		if(signChange) a=-a;
		//
		return a;
	}

}
