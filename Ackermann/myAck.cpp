#include "some.h"

//////////////////////////////////////////////////////////////////////////////////

int totalZ = 0;
int totalN = 0;
int totalU = 0;
int totalS = 0;
int totalR = 0;
int totalM = 0;

#define DEBUG_MODE 0

num Z(numList& x)
{
	if(DEBUG_MODE)
		totalZ++;
	return 0;
}

num N(numList& x)
{
	if (DEBUG_MODE)
		totalN++;
	return x[0] + 1;
}

template<int i>     
num U(numList& a)
{
	if (DEBUG_MODE)
		totalU++;
	return a[i - 1];
}

template<func f, func ... g>
num S(numList& a)
{
	if (DEBUG_MODE)
		totalS++;
	int f_size = sizeof...(g);
	int g_size = a.size();
	numList b;
	func g_i[sizeof...(g)] = { g... };
	for (int i = 0; i < f_size; i++)
		b.push_back(g_i[i](a));
	return f(b);
}

template<func f, func g>
num R(numList& b)
{
	if (DEBUG_MODE)
		totalR++;
	numList a(b);
	num y = a[a.size() - 1];
	a.pop_back();
	num k = f(a);
	a.push_back(0);
	a.push_back(k);
	for (int i = 0; i < y; i++)
	{
		k = g(a); 
		a[a.size() - 2]++;		
		a[a.size() - 1] = k;
	}
	return k;
}

template<func f>
num M(numList& b)
{
	if (DEBUG_MODE)
		totalM++;
	numList a(b);
	a.push_back(0);
	for (int i = 0; i < MAX_MINIMIZE; i++)							//    is it useful?
	{
		a[a.size() - 1] = i;
		if (f(a) == 0)
			return i;
	}
	std::cout << "M error  -  my max reached\n";
	exit(0);
	return 0;
}

///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////
///////////////////////////////////////////////////////

num sum(numList& a)
{
	//return R<U<1>, S<N, U<3>>>(a);
	//   O  =  b
	return a[0] + a[1];
}

num mul(numList& a)
{
	//return R<Z, S<sum, U<1>, U<3>>>(a);
	return a[0] * a[1];	
	//   O  =  a*b
}

num power(numList& a)
{
	return R<S<N, Z>, S<mul, U<1>, U<3>>>(a);
	//   O  =  a*a*b
}

num decrease(numList& a)  //maybe M
{
	//return R<Z, U<1>>(a);
	return max((long long)0, a[0] - 1);
	//   O  =  a
}

num substract(numList& a)  //maybe M
{
	//return R<U<1>, S<decrease, U<3>>>(a);
	return max((long long)0, a[0] - a[1]);
	//   O  =  a*b
}



///////////////////////////////////////
///////////////////////////////////////


num equalZero(numList& a)
{
	//return R<S<N, Z>, Z>(a);
	return (a[0] == 0);
	//   O  =  a
}

num moreThanZero(numList& a)
{
	//return R<Z, S<N, Z>>(a);
	return (a[0] > 0);
	//   O  =  a
}

num equalOne(numList& a)
{
	//return S<mul, moreThanZero, S<equalZero, decrease>>(a);
	return (a[0] == 1);
	//   O  =  a
}

num equal(numList& a)
{
	//return S<equalZero, S<sum, S<substract, U<1>, U<2>>, S<substract, U<2>, U<1>>>>(a);
	return (a[0] == a[1]);
	//   O  =  a*b
}

num more(numList& a)
{
	//return S<moreThanZero, S<substract, U<1>, U<2>>>(a);
	return (a[0] > a[1]);
	//   O  =  a*b
}

num moreOrEqual(numList& a)
{
	//return S<sum, more, equal>(a);
	return (a[0] >= a[1]);
	//   O  =  a*b
}

num moreThanOne(numList& a)         //              test
{
	//return S<moreThanZero, S<substract, moreThanZero, equalOne>>(a);
	return (a[0] > 1);
	//   O  =  a
}

num notEqual(numList& a)
{
	//return S<equalZero, equal>(a);
	return (a[0] != a[1]);
	//   O  =  a
}

///////////////////////////////////////
///////////////////////////////////////

num divide(numList& a)                                                                                      
{
	//return S<decrease, M<S<moreOrEqual, U<1>, S<mul, U<2>, U<3>>>>>(a);
	if(a[1] == 0)
		return 0;	
	else
		return (a[0] / a[1]);
	//   O  =  a^3 * b                          ans <= a   &&   O = a*b* ans^2
}

num mod(numList& a)
{
	return S < substract, U<1>, S<mul, S<divide, U<1>, U<2>>, U<2>> > (a);
	//   O  = a^3 * b
}

///////////////////////////////////////
///////////////////////////////////////

num One(numList& a)
{
	return S<N, Z>(a);
}

num Two(numList& a)
{
	return S<N, S<N, Z> >(a);
}

num Three(numList& a)
{
	return S<N, Two>(a);
}

num Four(numList& a)
{
	return S<N, Three>(a);
}

num Five(numList& a)
{
	return S<N, S<N, Three> >(a);
}

num MagicConst(numList& a)
{
	return S<mul, Four, Two>(a);
}


num unZero(numList& a)
{
	return S<sum, U<1>, S<equalZero, U<1>> >(a);
}

///////////////////////////////////////
///////////////////////////////////////

// to here

num dividersCount(numList& a)
{
	return S<R<Z, S<sum,    S<substract, S<equalZero, S<mod, U<1>, S<unZero, U<2>>>>, S<equalZero, U<2>>>,     U<3>>>, U<1>, U<1>>(a);
}

num isPrime(numList& a)
{
	return S<equalOne, dividersCount>(a);
}


num nextPrimePrimitive(numList& a)
{
	return S<R<Z, S<sum, S<mul, S<mul, S<sum, U<1>, U<2>>, S<isPrime, S<sum, U<1>, U<2>>>>, S<equalZero, U<3>>>, U<3>>>, U<1>, S<sum, U<1>, S<N, S<N, Z>>> >(a);
}


num nextPrime(numList& a)
{
	return S<sum, M<S<equalZero, S<isPrime, S<sum, sum, One>>>>, S<sum, U<1>, One>>(a);
}

num nthPrime(numList& a)
{
	return S<R<S<N, S<N, Z>>, S<nextPrime, U<3>>>, U<1>, U<1>>(a);
}


///////////////////////////////////////
///////////////////////////////////////

num plog(numList& a)                //  second is base
{
	return S<decrease, M<S<equalZero, S<mod, U<1>, S<power, U<2>, U<3> >>  >>>(a);
}

///////////////////////////////////////
///////////////////////////////////////

num ifElse0(numList& a)
{
	return S<R<Z, U<1>>, U<2>, S<moreThanZero, U<1>>>(a);
}


///////////////////////////////////////
///////////////////////////////////////

num makeStack(numList& a)
{
	return S<mul,    S<mul, Four, S<power, Three, U<1> >  >  , S<power, Five, U<2> > >(a);
}

num stackSize(numList& a)
{
	return S<plog, U<1>, Two>(a);
}

num getLast(numList& a)
{
	return S<plog, U<1>, S<nthPrime, stackSize> >(a);
}

num getPrevLast(numList& a)
{
	return S<ifElse0, S<moreThanOne, stackSize>, S<plog, U<1>, S<nthPrime, S<substract, stackSize, One>> >>(a);
}

num push(numList& a)        
{
	return S<mul, S<mul, U<1>, Two>, S<power, S<nthPrime, S<sum, stackSize, One>>, U<2> > >(a);  
}

num pop(numList& a)   
{
	return S<sum, S<ifElse0, S<moreThanZero, stackSize>, S<divide, S<divide, U<1>, S<power, S<nthPrime, stackSize>, getLast>  >, Two>>, S<equalOne, U<1>>>(a);
}

///////////////////////////////////////
///////////////////////////////////////

num stackStepM0(numList& a)
{
	return S<push, S<pop, pop>, S<sum, getLast, One>>(a);
}

num stackStepN0(numList& a)
{
	return S<push, S<push, S<pop, pop>, S<substract, getPrevLast, One>>, One>(a);
}

num stackStepMN(numList& a)
{
	return S<push, S<push, S<push, S<pop, pop>, S<substract, getPrevLast, One>>, getPrevLast >, S<substract, getLast, One>>(a);
}

//////////////

num outerstackStepM0(numList& a)
{
	return S<ifElse0, S<equalZero, getPrevLast>, stackStepM0>(a);
}

num outerstackStepN0(numList& a)
{
	return S<ifElse0, S<ifElse0, S<moreThanZero, getPrevLast>, S<equalZero, getLast>>, stackStepN0>(a);
}

num outerstackStepMN(numList& a)
{
	return S<ifElse0, S<ifElse0, S<moreThanZero, getPrevLast>, S<moreThanZero, getLast>>, stackStepMN>(a);
}

///////////////////////////////////////
///////////////////////////////////////

num stackStep(numList& a)
{
	return S<sum, S<ifElse0, S<moreThanOne, stackSize>, S<sum, outerstackStepM0, S<sum, outerstackStepN0, outerstackStepMN> > >,
		S<mul, S<substract, One, S<moreThanOne, stackSize>>, U<1>>  >(a);
}

num getAckSteps(numList& a)							
{
	return S<sum, S<power, Two, S<M<S<moreThanOne, S<stackSize,
		S<R<stackStep, S<stackStep, U<3>>>, U<1>, S<sum, S<power, Two, U<2>>, Five>  >
		>> >, makeStack >>, Five>(a);
}

num innerAck(numList& a)
{
	return S<R<stackStep, S<stackStep, U<3>>>, makeStack, getAckSteps>(a);
}

num ack(numList& a)
{
	return S<plog, innerAck, Three>(a);
}

///////////////////////////////////////
///////////////////////////////////////
///////////////////////////////////////
///////////////////////////////////////
///////////////////////////////////////
///////////////////////////////////////




void check(func f, num g, numList& a, string err)
{
	if (f(a) != g)
		cout << "ERROR  " << err << " right = " << g << "  my = "<<f(a)<< " with list " << a[0] << " " << (a.size() > 1 ? a[1] : -1) << endl;
}



vector<numList> t;
vector<numList> t1;
int sizes;

void onlyFirst()
{
	for (int i = 0; i < t.size(); i++)
	{
		numList a;
		a.push_back(t[i][0]);
		t1.push_back(a);
	}
}

/*
                   ack tests
a[0] = 0;
a[1] = 0;
t.push_back(a);
a[0] = 0;
a[1] = 1;
t.push_back(a);
a[0] = 0;
a[1] = 2;
t.push_back(a);
a[0] = 1;
a[1] = 0;
t.push_back(a);
a[0] = 2;
a[1] = 0;
t.push_back(a);
a[0] = 1;
a[1] = 1;
t.push_back(a);

*/

/*                    big sth
a[0] = 1;
a[1] = 0;
t.push_back(a);
a[0] = 1;
a[1] = 2;
t.push_back(a);
a[0] = 2;
a[1] = 3;
t.push_back(a);
a[0] = 4;
a[1] = 0;
t.push_back(a);
a[0] = 1;
a[1] = 2;
t.push_back(a);
a[0] = 2;
a[1] = 2;
t.push_back(a);
a[0] = 1;
a[1] = 1;
t.push_back(a);
a[0] = 16;
a[1] = 3;
t.push_back(a);
a[0] = 32;
a[1] = 4;
t.push_back(a);
a[0] = 60;
a[1] = 7;
t.push_back(a);
*/

void lightTests()
{
	numList a;
	a.push_back(0);
	a.push_back(0);

	a[0] = 0;
	a[1] = 13;
	t.push_back(a);
	a[0] = 1;
	a[1] = 5;
	t.push_back(a);
	a[0] = 2;
	a[1] = 4;
	t.push_back(a);
	a[0] = 2;
	a[1] = 1;
	t.push_back(a);
	a[0] = 1;
	a[1] = 3;
	t.push_back(a);
	a[0] = 3;
	a[1] = 1;
	t.push_back(a);

	sizes = t.size();
	onlyFirst();
}
 
int main()
{
	cout<<"Compiled"<<endl;
	srand(1212);
	double tt = 1.0 * clock() / CLOCKS_PER_SEC;

	lightTests();

	for (int i = 0; i < sizes; i++)
	{
		
		/*
		check(sum, t[i][0] + t[i][1], t[i], "sum");
		check(mul, t[i][0] * t[i][1], t[i], "mul");
		//check(power, -1, t[i], "power");														//  in zero power???
		check(decrease, max(t[i][0] - 1, 0), t1[i], "decrease");
		check(substract, max(t[i][0] - t[i][1], 0), t[i], "substract");
		*/
		///////////
		/*
		check(equalZero, (t[i][0] == 0), t1[i], "equalZero");
		check(moreThanZero, (t[i][0] > 0), t1[i], "moreThanZero");
		check(equalOne, (t[i][0] == 1), t1[i], "equalOne");
		check(moreThanOne, (t[i][0] > 1), t1[i], "moreThanOne");
		check(more, (t[i][0] > t[i][1]), t[i], "more");
		check(moreOrEqual, (t[i][0] >= t[i][1]), t[i], "moreOrEqual");
		check(equal, (t[i][0] == t[i][1]), t[i], "equal");
		check(notEqual, (t[i][0] != t[i][1]), t[i], "notEqual");
		*/		
		////////////
		/*
		if (t[i][1] != 0)
		{
			check(divide, t[i][0] / t[i][1], t[i], "divide");
			check(mod,  t[i][0] % t[i][1], t[i], "mod");
		}
		*/
		/////////////
		/*
		check(dividersCount, -1, t1[i], "dividersCount");
		check(isPrime, -1, t1[i], "isPrime");
		check(nextPrime, -1, t1[i], "nextPrime");
		check(nthPrime, -1, t1[i], "nthPrime");
		*/
		////////////
		/*
		if (t[i][0] > 1 && t[i][1] > 1)
			check(plog, -1, t[i], "plog");
		*/
		////////////
		/*
		check(makeStack, -1, t[i], "makeStack");	/*	
		check(stackSize, -1, t1[i], "stackSize");
		check(getLast, -1, t1[i], "getLast");
		
		check(push, -1, t[i], "push");                                         
		check(pop, -1, t1[i], "pop");                                           //test   pop  from  zero sized stack   gives 0 !!!!!!!!!!
		*/
		////////////

		//check(stackStep, -1, t1[i], "stackStep");        //   t ??   t1    
		
		//check(ifElse0, -1, t[i], "ifElse0");

		check(ack, -1, t[i], "ack");


		if (DEBUG_MODE)
		{
			cout << "_________\n";
			cout << "Z:" << totalZ << "\n";
			cout << "N:" << totalN << "\n";
			cout << "U:" << totalU << "\n";
			cout << "S:" << totalS << "\n";
			cout << "R:" << totalR << "\n";
			cout << "M:" << totalM << "\n";
		}
		////////////
		cout << "_________\n";
		cout << "Time : " << ((1.0 * clock() / CLOCKS_PER_SEC) - tt) << "\n";
		tt = (1.0 * clock() / CLOCKS_PER_SEC);
		////////////

		//check(plog, -1, t[i], "plog");

		std::cout << "\n";														//give to M vectors with right size
	}

	int xxx;
	cout << "Finished\n";
	cout << "Time : " << ((1.0 * clock() / CLOCKS_PER_SEC) - tt) << "\n";
	cin >> xxx;
	xxx++;

	return 0;
}
