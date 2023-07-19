import logging
import json
import os

from bs4 import BeautifulSoup
from urllib.request import urlopen, Request

logger = logging.getLogger(__name__)

def get_stock_picks(url: str) -> str:
    logger.info(f"Getting stock picks from: {url}")

    req = Request(
        url = url,
        headers = {'User-Agent': 'Mozilla/5.0'}
        )

    response = urlopen(req)

    if response.getcode() == 200:
        html = response.read().decode("utf-8")
        return parse_html(html)
    else:
        logger.warn("Request to url was not successful")
        return

def parse_html(html: str) -> str:
    logger.info("Parsing HTML")
    
    soup = BeautifulSoup(html, "html.parser")
    html = soup.find("div", class_="main-item-inner").find("ul", class_="trending-list").text

    # List of stock symbols only
    ticker_list = html.split()[1::2]
    json_tickers = json.dumps(ticker_list)

    return json_tickers


def notify_reporter(json: str, dest: str):
    logger.info(f"Notifying reporter service with payload: {json}")

    post_data = json.encode("utf-8")
    
    aws_req = Request(
        url = dest,
        data = post_data,
        headers = {'User-Agent': 'Mozilla/5.0', 'Content-Type': 'application/json', 'Accept':'application/json'}
        )

    response = urlopen(aws_req)
    logger.info(f"Request has response code {response.getcode()}")

    
if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO)
    quiver_url = "https://www.quiverquant.com/"
    reporter_url = "http://localhost:5000/stonks"
    
    data = get_stock_picks(quiver_url)
    notify_reporter(data, reporter_url)

    logger.info("Scraper has finished scraping successfully!")


    
