import os
import json

class FileMaker:
    def __init__(self, file_name):
        self.file = None
        self._dict = {}
        try:
            if not os.path.exists(os.getcwd() + '/output'):
                os.makedirs(os.getcwd() + '/output')
            self.file_name = os.getcwd() + '/output/' + file_name + '.json'
            #os.ßmakedirs(os.path.dirname(file_name), exist_ok=True)
            #print(self.file_name)
            self.file = open(self.file_name, 'a+')
        except (OSError, IOError) as err:
            print err

    def __del__(self):
        if self.file != None:
            self.write_json()
            self.file.close()

    def write_json(self):
        #dict_as_json = json.dumps(dictionary, separators=(',',':'), indent=2)
        #print(prop)
        #Convert to a string to write it?
        self.file.write(json.dumps(self._dict, separators=(',', ':'), indent=2))

    def add_to_dict(self, dictionary):
        #dict_as_json = json.dumps(dictionary, separators=(',',':'), indent=2)
        prop = {dictionary['UUID']: dictionary}
        self._dict[dictionary['UUID']] = dictionary
        print prop
        #Maybe open file?
        #Convert to a string to write it?
        #self.file.write(json.dumps(prop, indent=2))

    def generate(self, dictionary):
        self.add_to_dict(dictionary)
