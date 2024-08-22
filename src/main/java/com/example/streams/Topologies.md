## Wordcount
```
our topology: Topologies:
   Sub-topology: 0
    Source: KSTREAM-SOURCE-0000000000 (topics: [streams-plaintext-input])
      --> KSTREAM-FLATMAPVALUES-0000000001
    Processor: KSTREAM-FLATMAPVALUES-0000000001 (stores: [])
      --> KSTREAM-KEY-SELECT-0000000002
      <-- KSTREAM-SOURCE-0000000000
    Processor: KSTREAM-KEY-SELECT-0000000002 (stores: [])
      --> counts-store-repartition-filter
      <-- KSTREAM-FLATMAPVALUES-0000000001
    Processor: counts-store-repartition-filter (stores: [])
      --> counts-store-repartition-sink
      <-- KSTREAM-KEY-SELECT-0000000002
    Sink: counts-store-repartition-sink (topic: counts-store-repartition)
      <-- counts-store-repartition-filter

  Sub-topology: 1
    Source: counts-store-repartition-source (topics: [counts-store-repartition])
      --> KSTREAM-AGGREGATE-0000000003
    Processor: KSTREAM-AGGREGATE-0000000003 (stores: [counts-store])
      --> KTABLE-TOSTREAM-0000000007
      <-- counts-store-repartition-source
    Processor: KTABLE-TOSTREAM-0000000007 (stores: [])
      --> KSTREAM-SINK-0000000008
      <-- KSTREAM-AGGREGATE-0000000003
    Sink: KSTREAM-SINK-0000000008 (topic: streams-wordcount-output)
      <-- KTABLE-TOSTREAM-0000000007
```