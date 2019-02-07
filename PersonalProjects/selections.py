import csv
import random
import math
from site_class import Site
from member_class import Member

class ServeSelections:


    def __init__(self):
        self.member_list = list()
        self.lottery_numbers = list()
        self.site_list = list()
        self.csv_array = []
        self.pref_count = [0,0,0,0,0,0,0]
        self.assignment_count = 0
        self.error_value = 0


    def parse_csv(self, filepath):
        with open(filepath) as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                self.csv_array.append(row)

    def create_sites(self):
        site_names = []
        for row in self.csv_array[1:]:
            for col in range(2,9):
                if row[col] not in site_names:
                    site_names.append(row[col])
        for new_name in site_names:
            site = Site(name = new_name)
            self.site_list.append(site)

    def create_all_members(self, csv_data):
     # 1) Assign name
     # 2) Assign each preference
        for row in self.csv_array[1:]:
            name = row[1]
            pref_list = list()
            for pref in row[2:9]:
                pref_list.append(pref)
            self.member_list.append(Member(name = name, pref_list = pref_list))

    def manually_assign_site(self, name, pref_number):   # call with range 1-7
        member = next(i for i in self.member_list if name in i.name)
        for site in self.site_list:
            if site.name == member.pref_list[pref_number - 1]:
                member.assignment = site.name
                member.pref_number = pref_number
                site.spots_left -= 1
                site.member_list.append(member.name)
        self.assignment_count += 1

    def assign_remaining_sites(self):
        self._assign_lottery_numbers()
        while self.lottery_numbers:
            next_number = min(self.lottery_numbers)
            print("The next number is: ", next_number)
            self.lottery_numbers.remove(next_number)
            next_member = next(mem for mem in self.member_list if mem.lottery_number == next_number)
            print("The person with this number is: ", next_member.name)
            self._match_member(next_member)

    def _assign_lottery_numbers(self):
        self.lottery_numbers = list(range(0,20 - self.assignment_count))
        random.shuffle(self.lottery_numbers)
        current_index = 0
        for member in self.member_list:
            if member.assignment == "":
                member.lottery_number = self.lottery_numbers[current_index]
                print(member.name, " gets the number ", member.lottery_number)
                current_index += 1

    def _match_member(self, member):
        pref_number = -1
        for pref in member.pref_list:
            pref_number += 1
            for site in self.site_list:
                if pref == site.name and site.spots_left > 0 and member.no_conflict(site):
                    member.assignment = site.name
                    member.pref_number = pref_number
                    site.spots_left -= 1
                    site.member_list.append(member.name)
                    print(member.name, " got ", site.name)
                    return
                elif pref == site.name and not member.no_conflict(site):
                    print("######################################")
                    print(member.name, " HAD A CONFLICT AT ", site.name)
                    print("######################################")
                    print(member.name, " wanted ", site.name, " but didn't get it")
                elif pref == site.name and site.spots_left <= 0:
                    print(member.name, " wanted ", site.name, " but didn't get it")

    def count_prefs(self):
        for member in self.member_list:
            pref = member.pref_number
            print(member.name, " got pref #: ", pref)
            self.pref_count[pref] += 1
        for i in range(0, len(self.pref_count)):
            self.error_value += (self.pref_count[i] * math.pow(math.e, i))
        print(self.error_value)



    def print_values(self):
        print("Site List: ", [i.name for i in self.site_list])
        print("Member List: ", [i.name for i in self.member_list])
        print("Lottery numbers left: ", [i for i in self.lottery_numbers])
        print("Pref count: ", [i for i in self.pref_count])
        for site in self.site_list:
            print(site.name, ": ", site.member_list)

    def write_to_csv(self):
        array = []
        for site in self.site_list:
            site_row = [site.name]
            for member in site.member_list:
                site_row.append(member)
            array.append(site_row)
        pref_row = ["pref row"]
        for pref in self.pref_count:
            pref_row.append(pref)
        array.append(pref_row)
        array.append([self.error_value])
        with open('results.csv', mode = 'a', newline='') as file:
            writer = csv.writer(file, delimiter=',')
            writer.writerows(array)
            writer.writerow([])

    def write_to_csv_two(self):
        array = list("" for i in range(0,5))
        array[0] = ["Sites"]
        array[1] = ["Member 1"]
        array[2] = ["Member 2"]
        array[3] = ["Member 3"]
        for site in self.site_list:
            array[0].append(site.name)
            for i in range(0, len(site.member_list)):
                array[i + 1].append(site.member_list[i])
        array[4] = ["Pref List"]
        for pref in self.pref_count:
            array[4].append(pref)
        array[0].append(self.error_value)
        if(self.error_value < 37):
            for member in self.member_list:
                if member.pref_number > 1:
                    array[1].append("Third Choice: " + member.name)
            with open('results37.csv', mode = 'a', newline='') as file:
                writer = csv.writer(file, delimiter=',')
                writer.writerows(array)
                writer.writerow([])
        elif(self.error_value < 39):

            with open('results39.csv', mode = 'a', newline='') as file:
                writer = csv.writer(file, delimiter=',')
                writer.writerows(array)
                writer.writerow([])
        elif(self.error_value < 41):
            with open('results41.csv', mode = 'a', newline='') as file:
                writer = csv.writer(file, delimiter=',')
                writer.writerows(array)
                writer.writerow([])

    def write_to_csv_three(self):
        if self.error_value > 48: return
        array = list("" for i in range(0,5))
        array[0] = ["Sites"]
        array[1] = ["Member 1"]
        array[2] = ["Member 2"]
        array[3] = ["Member 3"]
        for site in self.site_list:
            array[0].append(site.name)
            for i in range(0, len(site.member_list)):
                array[i + 1].append(site.member_list[i])
        array[4] = ["Pref List"]
        for pref in self.pref_count:
            array[4].append(pref)
        array[0].append(self.error_value)
        for member in self.member_list:
            if member.pref_number > 1:
                array[1].append("Third Choice: " + member.name)
        with open('results3.csv', mode = 'a', newline='') as file:
            writer = csv.writer(file, delimiter=',')
            writer.writerows(array)
            writer.writerow([])

    def assign_conflict(self, name, conflict):
        member = next(i for i in self.member_list if name in i.name)
        member.set_conflict(conflict)

    def main(self):
        self.parse_csv("responses.csv")
        self.create_sites()
        print([i.name for i in self.site_list])
        self.create_all_members(csv_data=self.csv_array)
        self.manually_assign_site("Erin", 1)
        #self.assign_conflict("Nyssa", "Julia")
        #self.assign_conflict("Julia", "Nyssa")
        self.assign_remaining_sites()
        self.count_prefs()
        self.write_to_csv_two()
        self.write_to_csv_three()


    #if __name__ == "__main__": main()

results = list()
for i in range(0, 1000):
    results.append(ServeSelections())
for i in results:
    i.main()


