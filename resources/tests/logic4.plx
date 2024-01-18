int a;
int b;
int c;
a = 2;
b = 3;
c = 4;
if ( a<(b=a) || b<(c=b=a) ) {
   a = 5;
   if ((a<b || b<a) && (b<c || c<b)) 
   	    b = 6;
   if (!a==5 || ! a==b && !b==c) 
        c = 7;  
}
print(a*b*c);