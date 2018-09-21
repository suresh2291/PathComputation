package com.srmtech.catalist;
    //This is a java program to check whether a point lies in a polygon or not
    class Point

    {

        int x, y;

     

        Point()

        {}

     

        Point(int p, int q)

        {

            x = p;

            y = q;

        }

    }

     

    public class PointCheck

    {

     

        public static boolean onSegment(Point p, Point q, Point r)

        {

            if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)

                    && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))

                return true;

            return false;

        }

     

        public static int orientation(Point p, Point q, Point r)

        {

            int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

     

            if (val == 0)

                return 0;

            return (val > 0) ? 1 : 2;

        }

     

        public static boolean doIntersect(Point p1, Point q1, Point p2, Point q2)

        {

     

            int o1 = orientation(p1, q1, p2);

            int o2 = orientation(p1, q1, q2);

            int o3 = orientation(p2, q2, p1);

            int o4 = orientation(p2, q2, q1);

     

            if (o1 != o2 && o3 != o4)

                return true;

     

            if (o1 == 0 && onSegment(p1, p2, q1))

                return true;

     

            if (o2 == 0 && onSegment(p1, q2, q1))

                return true;

     

            if (o3 == 0 && onSegment(p2, p1, q2))

                return true;

     

            if (o4 == 0 && onSegment(p2, q1, q2))

                return true;

     

            return false;

        }

     

        public static boolean isInside(Point polygon[], int n, Point p)

        {

            int INF = 10000;

            if (n < 3)

                return false;

     

            Point extreme = new Point(INF, p.y);

     

            int count = 0, i = 0;

            do

            {

                int next = (i + 1) % n;

                if (doIntersect(polygon[i], polygon[next], p, extreme))

                {

                    if (orientation(polygon[i], p, polygon[next]) == 0)

                        return onSegment(polygon[i], p, polygon[next]);

     

                    count++;

                }

                i = next;

            } while (i != 0);

     

            return (count & 1) == 1 ? true : false;

        }

     

        public static void main(String args[])

        {
        	/*<shop "1"  coords = "3090,18640,6170,18640,6170,14710,3110,14710"  
        	<shop "2"  coords = "8180,18630,11100,18630,11100,14950,8180,14950" 
        	<shop "3"  coords = "8200,14820,8200,11140,11110,11140,11100,14820"
        	<shop "4"  coords = "3080,8870,6200,8840,6210,14600,3100,14630"  
        	<shop "5"  coords = "8220,11050,11100,11050,11100,7130,8220,7130"
        	<shop "6"  coords = "3100,4510,6210,4510,6210,8800,3100,8800" 
        	<shop "7"  coords = "8240,2960,11120,2960,11120,7020,8240,7020"  
        	<shop "8"  coords = "5490,80,5490,2850,11100,2850,11100,80" 
        	<shop "9"  coords = "90,70,5400,70,5410,2890,90,2890"  
        	<shop "10"  coords = "90,10950,3030,10950,3030,4530,90,4530" */
            Point polygon1[] = { new Point(818, 1863), new Point(1110, 1863),

                    new Point(1110, 1471), new Point(311, 1471) };

            int n = 4;

            Point p = new Point(462, 1511);
                 System.out.println("Point P(" + p.x + ", " + p.y

                    + ") lies inside polygon: " + isInside(polygon1, n, p));

        }

    }

    