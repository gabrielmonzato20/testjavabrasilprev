import requests
import unittest
from requests.auth import HTTPBasicAuth
unittest.TestLoader.sortTestMethodsUsing = None

url_base_user_server='http://localhost:8086/v1/clients'
base_id = [0]

class TestBrasilPrev(unittest.TestCase):

    def test1_cria_usuario(self): 
        r1 = requests.post(url_base_user_server ,json={"name":"Luffy","cpf": "86777385681" ,"adress":"west blue"}, auth = HTTPBasicAuth('test', 'teste11'))
        self.assertEqual(r1.status_code,201)

    def test2_retorna_cliente(self):
        r1 = requests.get(url_base_user_server, auth = HTTPBasicAuth('test', 'teste11') )
        clients = r1.json()
        self.assertEqual(r1.status_code,200)
        self.assertEqual(clients[0]['name'],"Luffy")
        base_id[0]=clients[0]['id']

    def test3_retorna_cliente_by_id(self):
        r1 = requests.get(url_base_user_server+"/"+str(base_id[0]), auth = HTTPBasicAuth('test', 'teste11') )
        clients = r1.json()
        self.assertEqual(r1.status_code,200)
        self.assertEqual(clients['name'],"Luffy")
    
    def test4_retorna_cliente_by_id_erro(self):
        r1 = requests.get(url_base_user_server+"/0", auth = HTTPBasicAuth('test', 'teste11') )
        self.assertEqual(r1.status_code,500)

    def test5_cria_multi_clientes(self):  
        r1 = requests.post(url_base_user_server ,json={"name":"Kaido","cpf": "58065984703" ,"adress":"west blue"}, auth = HTTPBasicAuth('test', 'teste11'))
        r2 = requests.post(url_base_user_server ,json={"name":"Zoro","cpf": "32232156036" ,"adress":"all blue"}, auth = HTTPBasicAuth('test', 'teste11'))
        self.assertEqual(r1.status_code,201)
        self.assertEqual(r2.status_code,201)

    def test6_retorna_multi_clientes(self):
        r1 = requests.get(url_base_user_server, auth = HTTPBasicAuth('test', 'teste11') )
        clients = r1.json()
        self.assertEqual(r1.status_code,200)
        self.assertEqual(len(clients),3)

    def test7_cria_cliente_cnpj_error(self):
        r1 = requests.post(url_base_user_server ,json={"name":"Big Mon","cpf": "867774385681" ,"adress":"west blue"}, auth = HTTPBasicAuth('test', 'teste11'))
        self.assertEqual(r1.status_code,404)
    
    def test8_update_cliente(self):
        r1 = requests.put(url_base_user_server +"/"+str(base_id[0]) ,json={"name":"LuffyGear4","cpf": "86777385681" ,"adress":"west blue"}, auth = HTTPBasicAuth('test', 'teste11'))
        self.assertEqual(r1.status_code,204)

    def test9_update_cliente_inexistente(self): 
        r1 = requests.put(url_base_user_server +"/0" ,json={"name":"LuffyGear4","cpf": "86777385681" ,"adress":"west blue"}, auth = HTTPBasicAuth('test', 'teste11'))
        self.assertEqual(r1.status_code,404)

    def test_deleta_cliente(self):
        r1 = requests.delete(url_base_user_server +"/"+str(base_id[0]) ,auth = HTTPBasicAuth('test', 'teste11'))
        self.assertEqual(r1.status_code,204)

    def test_deleta_cliente_inexistente(self):
        r1 = requests.delete(url_base_user_server +"/0",auth = HTTPBasicAuth('test', 'teste11'))
        self.assertEqual(r1.status_code,500)



def runTests():
        suite = unittest.defaultTestLoader.loadTestsFromTestCase(TestBrasilPrev)
        unittest.TextTestRunner(verbosity=2,failfast=False).run(suite)

if __name__ == '__main__':
    runTests()