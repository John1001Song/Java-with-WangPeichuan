## Test suite for a RawSocketsServer and JettyServer that provide RESTful API to expose hotel data.
## Requires python requests package.
## Modified by Prof. Karpenko from the file of Prof. Rollins

import sys
import requests
import unittest
import argparse
import json

class TestServer(unittest.TestCase):

        def __init__(self, testname, host, port):
            super(TestServer, self).__init__(testname)
            self.host = host
            self.port = port

        # Testing invalid enpoint /hohoho - your code should return HTTP/1.1 404 Not Found
        def test_unknown_url(self):
            url = "http://" + self.host + ":" + self.port + "/hohoho"
            r = requests.get(url)
            self.assertEqual(r.status_code, 404)

        # Testing a post request - your code should return HTTP/1.1 405 Method not allowed
        def test_unimplemented_method(self):
            url = "http://" + self.host + ":" + self.port 
            r = requests.post(url)
            self.assertEqual(r.status_code, 405)

        # Test /hotelInfo endpoint ------------------------------------------------------
        # Tests a get request to /hotelInfo?hotelId=10323 and asserts that the response is HTTP/1.1 200 OK
        def test_hotelInfo_ok_httpstatus(self):
            url = "http://" + self.host + ":" + self.port + "/hotelInfo?hotelId=10323"
            r = requests.get(url)
            self.assertEqual(r.status_code, 200)
            
        # Tests a get request to /hotelInfo?hotelId=10323 and checks returned json
        def test_hotelInfo_ok_Info1(self):
            url = "http://" + self.host + ":" + self.port + "/hotelInfo?hotelId=10323"
            r = requests.get(url)
            self.assertEqual(r.status_code, 200)
            body = r.json()
            self.assertEqual(body['hotelId'], '10323')
            self.assertEqual(body['name'], 'Hilton Garden Inn San Francisco/Oakland Bay Bridge')
            self.assertEqual(body['addr'], '1800 Powell Street')
            self.assertEqual(body['city'], 'Emeryville')
            self.assertEqual(body['state'], 'CA')
            self.assertEqual(body['success'], True)

        # Tests a get request to /hotelInfo?hotelId=1003 and checks returned json
        def test_hotelInfo_ok_Info2(self):
            url = "http://" + self.host + ":" + self.port + "/hotelInfo?hotelId=1003"
            r = requests.get(url)
            self.assertEqual(r.status_code, 200)
            body = r.json()
            self.assertEqual(body['hotelId'], '1003')
            self.assertEqual(body['name'], 'Hotel Kabuki - a Joie de Vivre Boutique Hotel')
            self.assertEqual(body['addr'], '1625 Post St')
            self.assertEqual(body['city'], 'San Francisco')
            self.assertEqual(body['state'], 'CA')
            self.assertEqual(body['success'], True)      

        # Tests a get request to /hotelInfo?hotelId=10 where hoteId is invalid and asserts success = false
        def test_hotelInfo_invalidId(self):
            url = "http://" + self.host + ":" + self.port + "/hotelInfo?hotelId=10"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['hotelId'], 'invalid')
            self.assertEqual(body['success'], False)

        # Tests a get request to /hotelInfo where hoteId is missing and asserts success = false
        def test_hotelInfo_missingId(self):
            url = "http://" + self.host + ":" + self.port + "/hotelInfo"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['hotelId'], 'invalid')
            self.assertEqual(body['success'], False)      

        # Test /reviews endpoint ------------------------------------------------------
        # Tests a get request to /reviews?hotelId=10323&num=2 and asserts that the response is HTTP/1.1 200 OK
        def test_reviews_ok_httpstatus(self):
            url = "http://" + self.host + ":" + self.port + "/reviews?hotelId=10323&num=2"
            r = requests.get(url)
            self.assertEqual(r.status_code, 200)
            
        # Tests a get request to /reviews?hotelId=10323&num=2 and checks returned json
        def test_reviews_ok_Info1(self):
            url = "http://" + self.host + ":" + self.port + "/reviews?hotelId=10323&num=2"
            r = requests.get(url)
            self.assertEqual(r.status_code, 200)
            body = r.json()
            self.assertEqual(body['hotelId'], '10323')
            self.assertEqual(len(body['reviews']), 2)
            reviews = body['reviews'] 
            review0 = reviews[0]
            review1 = reviews[1]
            self.assertEqual(body['success'], True)

            # Review 0
            self.assertEqual(review0['user'], 'Bob ')
            self.assertEqual(review0['title'], 'Easy access to SF and dinning')
            self.assertEqual(review0['reviewId'], '57b35da57d9f220b07331325')
            self.assertEqual(review0['reviewText'], "This was our third use of this facility and again we enjoyed our stay.  The facility is comfortable and kept well.  We  are retired and the price and  'ease of use' are important for us.  Not only does the hotel meet our needs, there\'s attractive shopping and dinning within a few blocks.  The proximity to the Bay Bridge is appealing.  There are a number of historic and entertaining site near this location, i.e., Jack London Square (Oakland); Pier 39 (SF); and Rosie the Rivitor NP (Richmond).")
            # Review 1
            self.assertEqual(review1['user'], 'Anonymous')
            self.assertEqual(review1['title'], 'Good location')
            self.assertEqual(review1['reviewId'], '57a6d8ec849f110b28370b7a')
            self.assertEqual(review1['reviewText'], 'Needed a reasonable place just outside of SF to rest.  We ended up getting in too late to use the pool, but the room was comfortable.')

        # Tests a get request to /reviews?hotelId=10323&num=1 and checks returned json
        def test_reviews_ok_Info2(self):
            url = "http://" + self.host + ":" + self.port + "/reviews?hotelId=10323&num=1"
            r = requests.get(url)
            self.assertEqual(r.status_code, 200)
            body = r.json()
            self.assertEqual(body['hotelId'], '10323')
            self.assertEqual(len(body['reviews']), 1)
            reviews = body['reviews'] 
            review0 = reviews[0]
            self.assertEqual(body['success'], True)
            # Review 0
            self.assertEqual(review0['user'], 'Bob ')
            self.assertEqual(review0['title'], 'Easy access to SF and dinning')
            self.assertEqual(review0['reviewId'], '57b35da57d9f220b07331325')
            self.assertEqual(review0['reviewText'], "This was our third use of this facility and again we enjoyed our stay.  The facility is comfortable and kept well.  We  are retired and the price and  'ease of use' are important for us.  Not only does the hotel meet our needs, there\'s attractive shopping and dinning within a few blocks.  The proximity to the Bay Bridge is appealing.  There are a number of historic and entertaining site near this location, i.e., Jack London Square (Oakland); Pier 39 (SF); and Rosie the Rivitor NP (Richmond).")
           
            
        # Tests a get request to /reviews?hotelId=10&num=2 where hoteId is invalid and asserts success = false
        def test_reviews_invalidId(self):
            url = "http://" + self.host + ":" + self.port + "/reviews?hotelId=10&num=2"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['hotelId'], 'invalid')
            self.assertEqual(body['success'], False)

        # Tests a get request to /reviews where parameters are missing and asserts success = false
        def test_reviews_missingParameters(self):
            url = "http://" + self.host + ":" + self.port + "/reviews"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['hotelId'], 'invalid')
            self.assertEqual(body['success'], False)

        # Tests a get request to /reviews where hotel id is missing and asserts success = false
        def test_reviews_missingId(self):
            url = "http://" + self.host + ":" + self.port + "/reviews?num=2"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['hotelId'], 'invalid')
            self.assertEqual(body['success'], False)

        # Tests a get request to /reviews where num parameter is missing and asserts success = false
        def test_reviews_missingNum(self):
            url = "http://" + self.host + ":" + self.port + "/reviews?hotelId=10323"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['hotelId'], 'invalid')
            self.assertEqual(body['success'], False)

         # Tests a get request to /attractions where hoteId is missing and asserts success = false
        def test_attractions_missingId(self):
            url = "http://" + self.host + ":" + self.port + "/attractions"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['hotelId'], 'invalid')
            self.assertEqual(body['success'], False)      

         # Tests a get request to /attractions where a radius is missing and asserts success = false
        def test_attractions_missingRad(self):
            url = "http://" + self.host + ":" + self.port + "/attractions?hotelId=10323"
            r = requests.get(url)
            body = r.json()
            self.assertEqual(body['radius'], 'invalid')
            self.assertEqual(body['success'], False)      

        # Tests a get request to /attractions?hotelId=10323&rad=2 and checks returned json
        def test_attractions_ok(self):
            url = "http://" + self.host + ":" + self.port + "/attractions?hotelId=10323&radius=2"
            r = requests.get(url)
            self.assertEqual(r.status_code, 200)
            body = r.json()
            attractions = body['results'] 
            # we will check only the first three attractions
            attr1 = attractions[0]
            attr2 = attractions[1]
            attr3 = attractions[2]

            # Attraction 1
            self.assertEqual(attr1['formatted_address'], '4050 Harlan St, Emeryville, CA 94608, United States')
            self.assertEqual(attr1['name'], 'Peloton Isle')
                       

            # Attraction 2
            self.assertEqual(attr2['formatted_address'], 'W Frontage Rd & Access Rd, Emeryville, CA 94608, United States')

            self.assertEqual(attr2['name'], 'Shorebird Park Emeryville')

             # Attraction 3
            self.assertEqual(attr3['formatted_address'], '3310 Powell St, Emeryville, CA 94608, United States')
        
            self.assertEqual(attr3['name'], 'Shark Diving International')
               
                 
args = None

if __name__ == "__main__":

        if(len(sys.argv) != 3):
            print ("usage: python http_server_test.py <host> <port>")
            sys.exit()

        host = sys.argv[1]
        port = sys.argv[2]
        print ("host: " + host + " port: " + port)

        suite = unittest.TestSuite()
        suite.addTest(TestServer("test_unknown_url", host, port))
        suite.addTest(TestServer("test_unimplemented_method", host, port))
        # Testing /hotelInfo
        suite.addTest(TestServer("test_hotelInfo_ok_httpstatus", host, port))
        suite.addTest(TestServer("test_hotelInfo_ok_Info1", host, port))
        suite.addTest(TestServer("test_hotelInfo_ok_Info2", host, port))
        suite.addTest(TestServer("test_hotelInfo_invalidId", host, port))
        suite.addTest(TestServer("test_hotelInfo_missingId", host, port))

        # Testing /reviews
        suite.addTest(TestServer("test_reviews_ok_httpstatus", host, port))
        suite.addTest(TestServer("test_reviews_ok_Info1", host, port))
        suite.addTest(TestServer("test_reviews_ok_Info2", host, port))
        suite.addTest(TestServer("test_reviews_invalidId", host, port))
        suite.addTest(TestServer("test_reviews_missingParameters", host, port))
        suite.addTest(TestServer("test_reviews_missingId", host, port))
        suite.addTest(TestServer("test_reviews_missingNum", host, port))

        # Testing /attractions
        suite.addTest(TestServer("test_attractions_missingId", host, port))
        suite.addTest(TestServer("test_attractions_missingRad", host, port))
        suite.addTest(TestServer("test_attractions_ok", host, port))
        
        unittest.TextTestRunner().run(suite)

