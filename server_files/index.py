import urllib
import time
import foodserviceRequestHandler
from mod_python import Cookie, apache
from mod_python import util

#import phd2RequestHandler

def index(req):
	"""Function that receives apache requests directly"""
	req.log_error('handler')
	
	handler = foodserviceRequestHandler.RequestHandler(req)
	#handler = phd2RequestHandler.RequestHandler(req)
	#req.write("\n"+req.uri+"\n")
	#return apache.OK

	return handler.run()
