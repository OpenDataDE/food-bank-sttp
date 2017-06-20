from FileMaker import FileMaker
from Parser import Parser
from GoogleRequest import GoogleRequest

def main():
    # Create file named 'user' in '/output'
    user_json = FileMaker('user')
    # Create file named 'partner' in '/output'
    partner_json = FileMaker('partner')
    # Create file named 'distributor' in '/output'
    distributor_json = FileMaker('restaurants')
    # Return an array of partners to this variable via the partners.csv
    partners_queue = Parser().parse_csv()
    # Return array of distributors to this variable
    establishment_queue = [e for e in GoogleRequest.pull_restaurants(partners_queue)]
    #print(establishment_queue)
    # For every partner in the partners queue array
    for partner in partners_queue:
        # Generate a JSON file for the partner and add it to the existing partner file
        user_json.generate(partner.to_user_json())
        partner_json.generate(partner.to_identification_json())

    # Generate a file for the establishments
    for establishment in establishment_queue:
        user_json.generate(establishment.to_user_json())
        distributor_json.generate(establishment.to_identification_json())

    # Create a json using the array of partners
    #partner_json(partners_queue)

    # Create a json using the array of establishments
    distributor_json.generate(establishment_queue)

if __name__ == '__main__':
    main()
