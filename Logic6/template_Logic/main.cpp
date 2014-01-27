#include "some.h"

//////////////////////////////////////////////////////////////////////////////////

num Z(numList& x)
{
	return 0;
}

num N(numList& x)
{
	return x[0] + 1;
}

template<int i, int n>        //TODO   n is useless
num U(numList& a)
{
	if (i > n || i < 1)
	{
		std::cout << "Bad U : i = " << i << "  n = " << n<<"\n";
		return 0;
	}
	return a[i - 1];
}

template<func f, func ... g>
num S(numList& a)
{
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
	numList a(b);
	int y = a[a.size() - 1];
	a[a.size() - 1] = 0;
	int k = f(a);
	a.push_back(k);
	for (int i = 1; i < y; i++)
	{
		a[a.size() - 2]++;
		k = g(a);
		a[a.size() - 1] = k;
	}
	return k;
}

template<func f>
num M(numList& b)
{
	numList a(b);
	a.push_back(0);
	std::cout << "M starts\n";
	for (int i = 0; /*i < MAX_MINIMIZE*/; i++)
	{
		a[a.size() - 1] = i;
		if (f(a) == 0)
			return i;
	}
	std::cout << "M finished\n";
	return 0;
}

///////////////////////////////////////////////////////

num sum(numList& a)
{
	return S<R<U<1, 2>, S<N, U<3, 3>>>, U<1, 1>, S<N, U<2, 2>>>(a);
}

num mul(numList& a)
{
	return S<R<Z, S<sum, U<1, 3>, U<3, 3>>>, U<1, 1>, S<N, U<2, 2>>>(a);
}

num power(numList& a)
{
	return S<R<S<N, Z>, S<mul, U<1, 3>, U<3, 3>>>, U<1, 1>, S<N, U<2, 2>>>(a);
}

num decrease(numList& a)
{
	return S<R<Z, U<2, 3>>, U<1, 1>, U<1, 1>>(a);
}

num substract(numList& a)
{
	return S<R<U<1, 1>, S<decrease, U<3, 3>>>, U<1, 1>, S<N, U<2, 2>>>(a);
}

num equalZero(numList& a)
{
	return S<R<S<N, Z>, Z>, U<1, 1>, S<N, U<1, 1>>>(a);
}

num moreThanZero(numList& a)
{
	return S<R<Z, S<N, Z>>, U<1, 1>, S<N, U<1, 1>>>(a);
}

num equalOne(numList& a)
{
	return S<mul, moreThanZero, S<equalZero, decrease>>(a);
}

num equal(numList& a)
{
	return S<equalZero, S<sum, S<substract, U<1, 1>, U<2, 2>>, S<substract, U<2, 2>, U<1, 1>>>>(a);
}

num notEqual(numList& a)
{
	return S<equalZero, equal>(a);
}

num divide(numList& a)
{
	return S < R < Z, 
		S<sum, S<mul, S<moreThanZero, S<substract, U<1, 1>, S<mul, U<2, 2>, U<3, 3>>>>, U<3, 3>>,
				S<mul, S<equalZero, S<substract, U<1, 1>, S<mul, U<2, 2>, U<3, 3>> > >, U<4, 4>>>   >, 
		S<N, U<1, 1>>, U<2, 2>, S<N, U<1, 1>>>(a);
}

num mod(numList& a)
{
	return S < substract, U<1, 1>, S<mul, S<divide, U<1, 1>, U<2, 2>>, U<2, 2>> > (a);
}

num dividersCount(numList& a)
{
	return S<R<Z, S<sum, S<equalZero, S<mod, U<1, 1>, U<2, 2>>>, U<3, 3>>>, U<1, 1>, U<1, 1>>(a);
}

num isPrime(numList& a)
{
	return S<equalOne, dividersCount>(a);
}

num nextPrime(numList& a)
{
	return S<R<Z, S<sum, S<mul, S<mul, S<sum, U<1, 1>, U<2, 2>>, S<isPrime, S<sum, U<1, 1>, U<2, 2>>>>, S<equalZero, U<3, 3>>>, U<3, 3>>>, U<1, 1>, S<sum, U<1, 1>, S<N, S<N, Z>>>>(a);
}

num nthPrime(numList& a)
{
	return S<R<S<N, S<N, Z>>, S<nextPrime, U<3, 3>>>, U<1, 1>, S<N, U<1, 1>>>(a);
}

num plog(numList& a)
{
	return S < R < Z,
		S<sum, S<mul, S<equalZero, S<mod, U<1, 1>, S<power, U<2, 2>, U<3, 3>>>>, U<3, 3>>,
		S<mul, S<moreThanZero, S<mod, U<1, 1>, S<power, U<2, 2>, U<3, 3>> > >, U<4, 4>>>   >,
		U<1, 1>, U<2, 2>, S<N, S<divide, U<1, 1>, U<2, 2>>>>(a);
}

void check(func f, num g, numList& a, string err)
{
	if (f(a) != g)
		cout << "ERROR  " << err << " right = " << g << "  my = "<<f(a)<< " with list " << a[0] << " " << a[1] << endl;
}

vector<numList> t;
int sizes;

void lightTests()
{
	sizes = 5;
	numList a;
	a.push_back(0);
	a.push_back(2);
	t.push_back(a);
	a[0] = 25;
	a[1] = 2;
	t.push_back(a);
	a[0] = 2;
	a[1] = 1;
	t.push_back(a);
	a[0] = 2;
	a[1] = 5;
	t.push_back(a);
	a[0] = 4;
	a[1] = 2;
	t.push_back(a);
}

void hardTests()
{	
	for (int i = 0; i < 10; i++)
	{
		numList a; 
		a.push_back(rand() % 100);
		a.push_back(rand() % 100);
		t.push_back(a);
	}
	numList a;
	a.push_back(0);
	a.push_back(1);
	t.push_back(a);
	a[0] = 5;
	a[1] = 0;
	t.push_back(a);
	sizes = 12;
}

int main()
{
	cout<<"Compiled"<<endl;
	srand(1212);

	lightTests();

	for (int i = 0; i < sizes; i++)
	{
		/*
		check(sum, t[i][0] + t[i][1], t[i], "sum");
		check(mul, t[i][0] * t[i][1], t[i], "mul");
		check(decrease, max(t[i][0] - 1, 0), t[i], "decrease");
		check(substract, max(t[i][0] - t[i][1], 0), t[i], "substract");
		check(equalZero, (t[i][0] == 0), t[i], "equalZero");
		check(moreThanZero, (t[i][0] > 0), t[i], "moreThanZero");
		*/
		if(t[i][1] != 0)
			check(divide, t[i][0] / t[i][1], t[i], "divide");
		if (t[i][1] != 0)
			check(mod, t[i][0] % t[i][1], t[i], "mod");
		/*
		check(equalZero, (t[i][0] == 0), t[i], "equalZero");
		check(moreThanZero, (t[i][0] > 0), t[i], "moreThanZero");
		check(equalOne, (t[i][0] == 1), t[i], "equalOne");
		*/
		/*
		check(dividersCount, -1, t[i], "dividersCount");
		check(isPrime, -1, t[i], "isPrime");*/
		/*
		check(nextPrime, -1, t[i], "nextPrime");
		check(nthPrime, -1, t[i], "nthPrime");*/
		/*
		check(power, -1, t[i], "power");
		if(t[i][1] > 1)
			check(plog, -1, t[i], "plog");*/

		
	}
	int xxx;
	cout << "Finished\n";
	cin >> xxx;
	xxx++;

	return 0;
}