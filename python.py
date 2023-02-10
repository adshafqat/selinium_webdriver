import undetected_chromedriver as uc
from pprint import pformat

driver = uc.Chrome(enable_cdp_events=True)

def mylousyprintfunction(eventdata):
    print(pformat(eventdata))
    
# set the callback to Network.dataReceived to print (yeah not much original)
driver.add_cdp_listener("Network.dataReceived", mylousyprintfunction)
driver.get('https://accounts.google.com/servicelogin')
search_form = driver.find_element_by_id("identifierId")
search_form.send_keys('mygmail')
nextButton = driver.find_elements_by_xpath('//*[@id ="identifierNext"]') 
search_form.send_keys('password')
nextButton[0].click() 