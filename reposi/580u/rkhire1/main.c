/* NAME : RISHIKESH KHIRE
  HOMEWORK NO ::: 2 
*/

#include<stdio.h>						// include standard i/o library
#define pi 3.141592						// Defined constant pi = 22/7 or 3.14
#define Es 30E6							// Defined constant Elasticity of Steel (Es) = 30x10^6

typedef struct{
	float stress;						// rod data type defined with stress and strain both as float
	float strain;
}rod;

/*
Function1   :::: To Find STRESS and STRAIN of Steel Rod
INPUT       :::: float d ----------> Diameter of Rod
		 float P ----------> LOAD on Steel Rod
RETURN VALUE ::: result  ----------> struct returnes conatains stress and strain
*/


rod function1(float d, float load){				// function to find Stress and Strain
	
								// pi=22/7 and Es of steel is 3x10^6 = 30E6
	rod result;						// result of type rod 
	float area , Oc;					// are and stress

		area = (pi*d*d)/4;				//area = pi * diameter^2
		Oc  = load/area;				// Compression Stress = load/area
	
		result.stress = Oc;				//Stress = Oc
		result.strain = Oc/Es;				//Strain = Stress/Oc
 
return result;							//return result of type rod
}

/*
FUNCTION2   :::: Function to calculate FORCE
INPUT	    :::: int d --------> Distance travelled to Stop
RETURN VALUE ::: Force as float  is returned
*/

float function2(int d){						// Function to calculate Force
	int vi = 100;						// Initial velocity = 100 m/sec
	int vo = 0;						// Final velocitu   = 0 m/sec
	int mass = 4000;					// Mass = 4000 pounds	
	float Force;						

		Force = mass * ((vi*vi - vo*vo)/(2*d));		// Force = Mass * ((Vi^2 - Vo^2)/(2*distance))
	
return Force;							// returning Force
}


/*
FUNCTION3  :::: Function to Calculate Pressure 
INPUT      :::: float depth ----------> depth 
RETURN VALUE :: Presure calculated for depth is returned
*/

float function3(float depth){					//Function to calculate Pressure
	
	float Pressure;		

		Pressure = (62.4 * depth)/144;			//Pressure = (62.4 * depth)/144	

return Pressure;						//return Pressure
}

/*----- MAIN FUNCTION----*/
int main(void){
	rod sample;													//Sample to type rod
	float d = 2;													// diameter as float intialized to value =2 for function 1
	float P;													// Load  "P" as float for function 1
	float Force; 													// Force as float to catch return value of function 2
	float Pressure;													// Pressure as float to catch return value of function 3
	float depth = 30;												// depth as float for function 3
	int distance;													// distance for function 2
/*---- LOOP TO CALCULATE STRESS & STRAIN for a Steel Rod for range of Load and Diameter  --------*/

	while(d <= 10)													//Loop till diameter is smaller than or equals to 10 & starts from diameter = 2
		           {
				        for(P = 50000; P <= 200000 ; P = P+25000)					//Loop till Load "p" <=200000 with increment of 25000 and starts from P = 50000
					{
						 sample = function1(d,P);						// function1 called and returned value  catched in sample
						 printf("%f,%f\n",sample.stress,sample.strain);			// STRESS and STRAIN printed 
					}
			  
			    d = d + 0.5;										//diameter  incremeted by 0.5
		          }

/*----- LOOP TO CALCULATE Force of CAR for range of distance ------*/		
	
	for(distance = 10; distance <= 100 ; distance = distance+10)							// Loop from distance =10 till distance smaller than or equals to 100 with increment of 10
	{
		 Force = function2(distance);										// function2 called and return value stored in Force
		 printf("\n%f",Force);											// Force printed
	}

	printf("\n");



/*----- LOOP TO CALCULATE PRESSURE for range of input depth ----*/
	do{														// Loop from depth =30 til depth smaller than or equals to 150

		Pressure = function3(depth);										//function3 called and returned value stored in Pressure										
		printf("\n%f", Pressure);									
		depth = depth + 10;											// loop incremented by 10

	  }while(depth <= 150);													
			
return 0;

}
