[![FOSSA Status](https://app.fossa.io/api/projects/custom%2B3903%2Fcodingbat%2F.svg?type=shield)](https://app.fossa.io/projects/custom%2B3903%2Fcodingbat%2F?ref=badge_shield)

# CodingBat
Download individual submitted code for questions from the CodingBat website.

## Running
### Clearer instructions forthcoming.
1. compile and run once to generate config file
2. provide details in config file (recommend verify yaml contents if not familiar with yaml)
3. run again and give it 30-60 seconds to fetch all of the questions on CodingBat

## Notes for Current Version
1. the current version is functional, but crude
2. only fetches Java questions
3. makes no distinction between questions you've completed and questions you haven't
4. will show errors if a question has comments in the code
5. does not produce code you can run as-is, that is, without creating your own test files

## Copyright Notices Regarding Dependencies
1. [jsoup 1.11.2](https://jsoup.org/), [MIT](https://opensource.org/licenses/MIT)
2. [google-java-format](https://github.com/google/google-java-format), [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0)