## Synopsis

This tool gives you the option to scrape user reviews from Amazon

## Code Example

private static void scrape(Scraper scraper, String[] urls) throws UnknownScrapeRequestException, IOException {
        for(String url : urls) {
            ScrapeRequestFactoryImpl requestFactory = new ScrapeRequestFactoryImpl();
            ScrapeRequest request = requestFactory.createRequest(url);
            scraper.scrape(request);
            request.getProduct().print();
        }
    }

## Getting Started

In order to run it you need to run it with arguments
https://www.amazon.com/DJI-Bundle-Shoulder-Charger-Batteries/dp/B01LZWGS9L/ref=sr_1_1?ie=UTF8&qid=1485381584&sr=8-1&keywords=mavic

The start point of the application is in the class ScrapperRunner

## Expand
This tool gives you the option to set strategy for scraping.
This is useful for example if the site have unit tests or if you want to scrape other websites.
In order to implement it you should implement two things
1) MyExtractor - which inherit from AbstractProductExtractorImpl
2) MyProduct - which inherit from AbstractProduct

Once you have your implementation you should create the relevant factory for it (ScrapeRequestFactoryImpl)
so the ScrapeRequest will have <b>URL</b> , the <b>Extractor</b> and the <b>Abstract Product</b>

##ToDo

Add more abstract methods for the abstract product like save it in the DB, export CSV and more

## Tests

Like always, can be better

## Contributors

ZivKaZiv

## License

MIT
Copyright (c) 2017 ZivKaZiv

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.