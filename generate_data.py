import csv
import uuid
import random
import string

def generate_stub_csv(filename, num_rows):
    # Define the column names
    columns = ['id', 'payer', 'payee', 'amount', 'currency', 'date', 'description']

    # Open the file in write mode
    with open(filename, mode='w', newline='') as file:
        writer = csv.writer(file)

        # Write the header
        writer.writerow(columns)

        # Generate the rows
        for _ in range(num_rows):
            row = [
                str(uuid.uuid4()),  # id
                ''.join(random.choices(string.ascii_uppercase + string.digits, k=10)),  # payer
                ''.join(random.choices(string.ascii_uppercase + string.digits, k=10)),  # payee
                round(random.uniform(10.0, 1000.0), 2),  # amount
                random.choice(['USD', 'EUR', 'GBP']),  # currency
                '2024-09-27',  # date (fixed for simplicity)
                'Payment for services'  # description
            ]
            writer.writerow(row)

# Example usage
generate_stub_csv('economic_payments.csv', 50000)