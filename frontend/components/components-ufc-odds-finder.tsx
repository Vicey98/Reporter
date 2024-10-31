'use client'

import React, { useState, useEffect, useMemo } from 'react'
import { Card, CardContent, CardHeader, CardTitle, CardFooter } from "@/components/ui/card"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Badge } from "@/components/ui/badge"
import { Swords, Calendar, Clock, Search, TrendingUp, AlertTriangle, Loader2, User } from 'lucide-react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Progress } from "@/components/ui/progress"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"

const UFCIcon = () => {
  return <Swords className="w-5 h-5 mr-2" />
}

interface Bookmaker {
  name: string;
  fighter1Odds: string;
  fighter2Odds: string;
}

interface Match {
  fighter1: string;
  fighter2: string;
  date: string;
  time: string;
  weightClass: string;
  bookmakers: Bookmaker[];
}

interface UFCBettingCardProps {
  match: Match;
}

const UFCBettingCard: React.FC<UFCBettingCardProps> = ({ match }) => {
  const getBestOdds = (fighterIndex: number): { odds: number; bookmaker: string } => {
    const bestOddsIndex = match.bookmakers.reduce((maxIndex, bookmaker, currentIndex, arr) => {
      const currentOdds = parseFloat(fighterIndex === 0 ? bookmaker.fighter1Odds : bookmaker.fighter2Odds);
      const maxOdds = parseFloat(fighterIndex === 0 ? arr[maxIndex].fighter1Odds : arr[maxIndex].fighter2Odds);
      return currentOdds > maxOdds ? currentIndex : maxIndex;
    }, 0);
    
    return {
      odds: parseFloat(fighterIndex === 0 ? match.bookmakers[bestOddsIndex].fighter1Odds : match.bookmakers[bestOddsIndex].fighter2Odds),
      bookmaker: match.bookmakers[bestOddsIndex].name
    };
  };

  const bestFighter1Odds = getBestOdds(0);
  const bestFighter2Odds = getBestOdds(1);

  const calculateImpliedOdds = (odds: number): number => {
    return (1 / odds) * 100;
  }

  const fighter1ImpliedOdds = calculateImpliedOdds(bestFighter1Odds.odds);
  const fighter2ImpliedOdds = calculateImpliedOdds(bestFighter2Odds.odds);
  const totalImpliedProbability = fighter1ImpliedOdds + fighter2ImpliedOdds;
  const hasArbitrageOpportunity = totalImpliedProbability < 100;

  const getOddsColor = (odds: number, bestOdds: { odds: number }): string => {
    const difference = (odds - bestOdds.odds) / bestOdds.odds;
    if (difference === 0) return 'text-blue-500 font-bold';
    if (difference <= 0.05) return 'text-yellow-500';
    return 'text-red-500';
  }

  return (
    <Card className={`h-full overflow-hidden shadow-lg transition-all duration-300 hover:shadow-xl ${hasArbitrageOpportunity ? 'bg-green-100 dark:bg-green-900' : 'bg-white dark:bg-gray-800'}`}>
      <CardHeader className="p-4">
        <div className="flex items-center justify-between mb-2">
          <Badge variant="outline" className={`text-xs font-semibold ${hasArbitrageOpportunity ? 'border-green-500 text-green-700 dark:text-green-300' : 'border-primary text-primary'}`}>
            <UFCIcon />
            UFC
          </Badge>
          <div className="flex items-center space-x-2 text-xs text-muted-foreground">
            <Calendar className="w-4 h-4" />
            <span>{new Date(match.date).toLocaleDateString('en-US', { month: 'short', day: 'numeric' })}</span>
            <Clock className="w-4 h-4 ml-2" />
            <span>{match.time}</span>
          </div>
        </div>
        <CardTitle className="text-xl font-bold text-foreground flex items-center justify-between">
          <div className="flex-1 pr-2">
            <div className="text-base sm:text-lg md:text-xl">{match.fighter1}</div>
            <div className="text-base sm:text-lg md:text-xl mt-1">vs {match.fighter2}</div>
          </div>
          {hasArbitrageOpportunity && (
            <AlertTriangle className="w-6 h-6 text-green-600 dark:text-green-400 flex-shrink-0" />
          )}
        </CardTitle>
        <p className="text-sm mt-2 font-medium text-muted-foreground">{match.weightClass}</p>
      </CardHeader>
      <CardContent className="p-4">
        <div className="mb-4">
          <div className="flex justify-between items-center mb-1">
            <span className="text-sm font-medium text-muted-foreground">Total Implied Probability:</span>
            <span className={`text-sm font-bold ${hasArbitrageOpportunity ? 'text-green-600 dark:text-green-400' : ''}`}>
              {totalImpliedProbability.toFixed(2)}%
            </span>
          </div>
          <Progress 
            value={totalImpliedProbability} 
            max={100} 
            className={`h-2 ${hasArbitrageOpportunity ? 'bg-green-200 dark:bg-green-700' : ''}`}
          />
          {hasArbitrageOpportunity && (
            <p className="text-sm text-green-600 dark:text-green-400 mt-1">
              Potential arbitrage opportunity!
            </p>
          )}
        </div>
        <Tabs defaultValue="best" className="w-full">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="best">Best Odds</TabsTrigger>
            <TabsTrigger value="odds">All Odds</TabsTrigger>
          </TabsList>
          <TabsContent value="best">
            <div className="grid grid-cols-2 gap-4 p-4">
              <div className="bg-gray-100 dark:bg-gray-700 p-4 rounded-lg">
                <p className="text-sm text-muted-foreground mb-1">{match.fighter1}</p>
                <p className="text-2xl font-bold text-green-600 dark:text-green-400">{bestFighter1Odds.odds.toFixed(2)}</p>
                <p className="text-xs text-muted-foreground mt-1">{bestFighter1Odds.bookmaker}</p>
              </div>
              <div className="bg-gray-100 dark:bg-gray-700 p-4 rounded-lg">
                <p className="text-sm text-muted-foreground mb-1">{match.fighter2}</p>
                <p className="text-2xl font-bold text-green-600 dark:text-green-400">{bestFighter2Odds.odds.toFixed(2)}</p>
                <p className="text-xs text-muted-foreground mt-1">{bestFighter2Odds.bookmaker}</p>
              </div>
            </div>
          </TabsContent>
          <TabsContent value="odds">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead className="w-[100px] text-xs font-bold">Bookmaker</TableHead>
                  <TableHead className="text-center text-xs font-bold">
                    <span className="hidden sm:inline">{match.fighter1}</span>
                    <span className="sm:hidden">Fighter 1</span>
                  </TableHead>
                  <TableHead className="text-center text-xs font-bold">
                    <span className="hidden sm:inline">{match.fighter2}</span>
                    <span className="sm:hidden">Fighter 2</span>
                  </TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {match.bookmakers.map((bookmaker, index) => (
                  <TableRow key={index}>
                    <TableCell className="font-medium text-xs">{bookmaker.name}</TableCell>
                    <TableCell 
                      className={`text-center text-xs ${getOddsColor(parseFloat(bookmaker.fighter1Odds), bestFighter1Odds)}`}
                    >
                      {parseFloat(bookmaker.fighter1Odds) === bestFighter1Odds.odds && (
                        <TrendingUp className="w-4 h-4 inline-block mr-1 text-green-500" />
                      )}
                      {bookmaker.fighter1Odds}
                    </TableCell>
                    <TableCell 
                      className={`text-center text-xs ${getOddsColor(parseFloat(bookmaker.fighter2Odds), bestFighter2Odds)}`}
                    >
                      {parseFloat(bookmaker.fighter2Odds) === bestFighter2Odds.odds && (
                        <TrendingUp className="w-4 h-4 inline-block mr-1 text-green-500" />
                      )}
                      {bookmaker.fighter2Odds}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TabsContent>
        </Tabs>
      </CardContent>
    </Card>
  )
}

const UfcOddsFinder: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [matches, setMatches] = useState<Match[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const mockData: Match[] = [
    {
      "fighter1": "Conor McGregor",
      "fighter2": "Dustin Poirier",
      "date": "2024-12-31",
      "time": "22:00 -05",
      "weightClass": "LIGHTWEIGHT BOUT",
      "bookmakers": [
        {
          "name": "Test Bookmaker A",
          "fighter1Odds": "2.10",
          "fighter2Odds": "1.95"
        },
        {
          "name": "Test Bookmaker B",
          "fighter1Odds": "2.15",
          "fighter2Odds": "1.90"
        }
      ]
    }
  ];

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/fights');
        if (!response.ok) {
          throw new Error('Failed to fetch matches');
        }
        const data = await response.json();
        setMatches([...mockData, ...data]);
      } catch (error) {
        console.error('Error fetching matches:', error);
        setMatches(mockData);
      } finally {
        setIsLoading(false);
      }
    };

    fetchMatches();
  }, []);

  const sortedAndFilteredMatches = useMemo(() => {
    const filtered = matches.filter(match =>
      match.fighter1.toLowerCase().includes(searchTerm.toLowerCase()) ||
      match.fighter2.toLowerCase().includes(searchTerm.toLowerCase()) ||
      match.weightClass.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return filtered.sort((a, b) => {
      const aImpliedProbability = (1 / Math.max(...a.bookmakers.map(b => parseFloat(b.fighter1Odds)))) + 
                                  (1 / Math.max(...a.bookmakers.map(b => parseFloat(b.fighter2Odds))));
      const bImpliedProbability = (1 / Math.max(...b.bookmakers.map(b => parseFloat(b.fighter1Odds)))) + 
                                  (1 / Math.max(...b.bookmakers.map(b => parseFloat(b.fighter2Odds))));
      
      if  (aImpliedProbability < 1 && bImpliedProbability >= 1) return -1;
      if (aImpliedProbability >= 1 && bImpliedProbability < 1) return 1;
      return aImpliedProbability - bImpliedProbability;
    });
  }, [matches, searchTerm]);

  return (
    <div className="min-h-screen bg-slate-100 dark:bg-slate-900 text-slate-900 dark:text-slate-100">
      <header className="bg-white dark:bg-slate-800 border-b border-gray-200 dark:border-gray-700">
        <div className="container mx-auto px-4 py-6">
          <div className="flex flex-col md:flex-row items-center justify-between mb-6">
            <div className="flex items-center mb-4 md:mb-0">
              <Swords className="w-10 h-10 mr-4 text-blue-600 dark:text-blue-400" />
              <h1 className="text-3xl md:text-4xl font-bold text-blue-600 dark:text-blue-400">UFC Odds Finder</h1>
            </div>
            <div className="flex items-center space-x-4">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
                <Input
                  type="search"
                  placeholder="Search fights..."
                  className="pl-10 w-64 bg-gray-100 dark:bg-gray-700 text-gray-900 dark:text-gray-100 border-gray-300 dark:border-gray-600 focus:border-blue-500 focus:ring-blue-500"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
              </div>
              <Button variant="outline" className="border-blue-500 text-blue-500 hover:bg-blue-500 hover:text-white">
                <User className="w-4 h-4 mr-2" />
                Login
              </Button>
            </div>
          </div>
          <div className="bg-slate-200 dark:bg-slate-700 p-6 rounded-lg shadow-lg">
            <h2 className="text-2xl font-semibold mb-2 text-blue-600 dark:text-blue-400">Upcoming Fights</h2>
            <p className="text-lg text-slate-700 dark:text-slate-300">
              Compare the latest UFC odds from top bookmakers for upcoming fights. Find the best values and potential arbitrage opportunities.
            </p>
          </div>
        </div>
      </header>
      <main className="container mx-auto px-4 py-12">
        {isLoading ? (
          <div className="flex justify-center items-center h-64">
            <Loader2 className="w-8 h-8 animate-spin text-blue-500" />
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {sortedAndFilteredMatches.map((match, index) => (
              <UFCBettingCard key={index} match={match} />
            ))}
          </div>
        )}
      </main>
      <footer className="bg-white dark:bg-slate-800 text-slate-600 dark:text-slate-400 py-8">
        <div className="container mx-auto px-4 text-center">
          <p className="text-lg">&copy; 2023 UFC Betting Odds Finder. All rights reserved.</p>
          <p className="mt-4 text-sm opacity-75">Disclaimer: Odds are for informational purposes only. Please bet responsibly.</p>
        </div>
      </footer>
    </div>
  )
}

export default UfcOddsFinder;