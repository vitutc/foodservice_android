from mod_python import util
#from htmlHandler import HtmlHandler
import string,cgi,time
from os import curdir, sep, path
import md5

#from topology import Topology
#from lg_topology import LG_Topology
#from stats import Stats
#from search import Search
#from xmlparser import XmlParser
#from vnetwork import vNetwork

class RequestHandler:

        def __init__(self, req):
                self.req = req
                self.getVarDict()
	        #self.tp = Topology()
    		#self.stats = Stats()
    		#self.lgt = LG_Topology()
		#self.vNetwork = vNetwork()
	
		#Folder image name inside main_dir (running dir)
		self.img_dir = "images"
		#Name of main xml
		self.main_xml_name = "main.xml"
		#Name of database file
		self.database_file_name = "main.db"

		self.mainPage = "home.html"
		self.funcDict = {'sanity':self.sanity,'getImage':self.getImage,'getMainXML':self.getMainXML,'getUserBalance':self.getUserBalance,'setUserBalance':self.setUserBalance,'checkUserPassword':self.checkUserPassword,'getDB':self.getDB}
		self.requestedFile = self.getRequestedFile()
                #Define script directory
                self.main_dir = path.abspath(__file__) 
                self.main_dir = self.main_dir[:self.main_dir.rfind(sep)]
		#Define request relative directory, e.g. images/<requestedFile> where images is inside main_dir
		#Note that this requires the apache configuration to point at /<example/, where anything after, like /<example_alias>/images/foo.img
		#Where <script_dir>/images/foo.img must be a system valid directory
		self.request_dir = self.req.uri[self.req.uri.find(sep,1):self.req.uri.rfind(sep)]
		#Get database to dict
		self.parseDB()

	def getRequestedFile(self):
		"""Returns the requested file from the URI"""
		if (self.req.uri[-1]==sep):
			return self.mainPage		
		return self.req.uri[self.req.uri.rfind(sep)+1:]

	def parseDB(self):
		"""Parses the db file and fills a dict
			Marks: - separate username from rest
			       | separate pairs of values
			       # end of line"""
		self.dbDict = {}		

		#Open File
		db_file = open(self.main_dir+sep+self.database_file_name,'r')
		for l in db_file:
			#Check if line is valid
			if len(l) > 10:
				#Get valid line
				validLine = l[:l.find("#")]
				userName = validLine.split("-")[0]
				self.dbDict[userName] = {}
				#Fill attributes for user
				attr_list = validLine.split("-")[1].split("|")
				for attr in attr_list:
					attr_name = attr.split(",")[0]
					attr_value = attr.split(",")[1]
					self.dbDict[userName][attr_name] = attr_value
		#Close File
		db_file.close()

		return self.dbDict
	
	def getDB(self):
		"Returns de DB in string format"
		self.req.content_type = "text/plain"
		self.req.write(str(self.dbDict))

	def updateDB(self):
		"Writes a filled db dict to a file"
		#Open file
		db_file = open(self.main_dir+sep+self.database_file_name,'w')
		
		#Prepare output list
		out_list = []
		
		#Iterate through users
		for user in self.dbDict.keys():
			attr_list = []
			for attr in self.dbDict[user].keys():
				attr_list.append(attr+","+self.dbDict[user][attr])
			outString = "%s-%s#" % (user,"|".join(attr_list))	
			out_list.append(outString)
		
		#Write result to file		
		db_file.write("\n".join(out_list)+"\n")		

		#Close file
		db_file.close()

        def getVarDict(self):
		"""Populates and returns a dictionary with arguments passed in the URI"""
                self.varDict = {}
                form = util.FieldStorage(self.req,keep_blank_values=1)
                for item in form.keys():
                                self.varDict[item] = form.get(item, None).value
                return self.varDict

	def Vars(self):
		"""Returns a string containing arguments of the request and its values"""
		if (not (self.varDict == None)):
			ret = "<br />"
			for key in self.varDict.keys():
				ret += ("<p> "+ key +" = "+ self.varDict[key]+ " </p>\n")
			return ret

        def sanity(self):
		"""Sanity test to check if the server is working correctly"""
                self.xmlHeader(self.req)
                self.req.send_http_header()
                varDict = self.getVarDict()

                self.req.write('<xml><result>It works.</result><varDict>'+str(varDict)+'</varDict></xml>')

                #return apache.OK
		return 		

        def xmlHeader(self, req):
		"""Prepares a XML response"""
                req.content_type = 'text/xml'
                req.write('<?xml version="1.0" standalone="yes"?>')

	def getImage(self):
		"Params: imageID -> ID of the image"
		imageID = self.varDict["imageID"]
		ret = open(self.main_dir+sep+self.img_dir+sep+imageID)
		self.req.write(ret.read())
	
	def getMainXML(self):
		"Params: None"
		ret = open(self.main_dir+sep+self.main_xml_name)
		self.req.content_type = 'text/xml'
		self.req.write(ret.read())

	def getUserBalance(self):
		"Params: userName -> Name of the user to get balance"
		userName = self.varDict["userName"]		
		self.req.write(self.dbDict[userName]["balance"])
	
	def setUserBalance(self):
		"""Params: userName -> Name of the user
			   newBalance -> New balance value"""
		userName = self.varDict["userName"]
		newBalance = self.varDict["newBalance"]
		self.dbDict[userName]["balance"] = newBalance
		self.updateDB()
		self.req.write("Ok")

	def checkUserPassword(self):
		"""Params: userName -> Name of the user
			   password -> Hashed password to check"""
		userName = self.varDict["userName"]
		password = self.varDict["password"]
		try:
			stored_password = self.dbDict[userName]["password"]
			self.req.write(str(password == stored_password))
		except:
			self.req.write("False")


        def run(self):
                """Resolves the request"""
		
		try:
			#Solve all types of client requests
			if self.requestedFile in self.funcDict.keys():
				self.funcDict[self.requestedFile]()
				return

			#If page is of ESP content (test)

			elif self.requestedFile.endswith(".esp"): 

				self.req.content_type = 'text/html'

				self.req.write("hey, today is the " + str(time.localtime()[7]))
				self.req.write(" day in the year " + str(time.localtime()[0]))
				self.req.write("\n")

				return

			#If page type not supported
					
			else:
		                self.req.content_type = "text/plain"

        	                self.req.write("404: File Not Found: " + self.request_dir + self.requestedFile + "\n")
	                        return

                

		except IOError,e:
			self.req.content_type = "text/plain"

			self.req.write("404: File Not Found: " + self.main_dir + sep + self.request_dir + sep + self.requestedFile + "\n")
			self.req.write("Erro: " + str(e) + "\n")
			return

