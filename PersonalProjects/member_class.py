from site_class import Site

class Member:

    __slots__ = ['name', 'assignment', 'pref_number', 'lottery_number', 'pref_list', 'conflict_list']

    def __init__(self, name, assignment = "", pref_number = 0, lottery_number = 0, pref_list = list()):
        self.name = name
        self.assignment = assignment
        self.pref_number = pref_number
        self.lottery_number = lottery_number
        self.pref_list = pref_list
        self.conflict_list = list()


    def set_conflict(self, conflict_name):
        self.conflict_list.append(conflict_name)


    def no_conflict(self, site):
        for conflict in self.conflict_list:
            for member in site.member_list:
                if conflict in member:
                    return False
        return True
