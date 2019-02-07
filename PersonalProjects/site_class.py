
class Site:

    __slots__ = ['name', 'max_spots', 'spots_left', 'member_list']


    def __init__(self, name, max_spots = 3):
        self.name = name;
        self.max_spots = max_spots;
        self.spots_left = max_spots;
        self.member_list = list()


