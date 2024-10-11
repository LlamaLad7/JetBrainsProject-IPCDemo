import random


def main():
    while True:
        match input():
            case "Hi":
                print("Hi")
            case "GetRandom":
                print(random.randint(1, 1000))
            case "Shutdown":
                return


if __name__ == '__main__':
    main()
