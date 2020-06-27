PUT /test
{
  "settings": {
    "number_of_shards": 2
    , "number_of_replicas": 3
  }
}

DELETE test

DELETE employ

PUT /employ
  "settings": {
    "number_of_shards": 1
    , "number_of_replicas": 1
  }
}

// 新建索引
PUT /employ/_doc/1
{
  "name":"邵田",
  "age":"23"
}

// 查询索引
GET /employ/_doc/1
GET /employ/_search

// POST查询全部索引
POST /employ/_search

POST /employ/_create/2
{
  "name":"邵哥",
  "age":23,
  "gender":"男"
}

POST /employ/_update/1
{
  "doc": {
     "age":22
  }
}

// 创建结构化索引
PUT /employ
{
  "settings": {
    "number_of_shards": 1
    , "number_of_replicas": 1
  }
  , "mappings": {
    "properties": {
      "name":{"type": "text"},
      "age":{"type": "integer"}
    }
  }
}

// 不带条件查询
GET /employ/_search
{
  "query": {
    "match_all": {}
  }
}

// 分页查询
GET /employ/_search
{
  "query": {
    "match_all": {}
  },
  "from": 1
  , "size": 1
}

// 带排序
GET /employ/_search
{
  "query": {
    "match": {
      "name":"邵哥"
    }
  }
}

// ordey排序
GET /employ/_search
{
  "query": {
    "match": {
      "name":"邵哥"
    }
  }
  , "sort": [
    {
      "age": {
        "order": "desc"
      }
    }
  ]
}

// filter过滤 = where
GET /employ/_search
{
  "query": {
    "bool": {
      "filter": [
        {"term":{"age":221}}
      ]
    }
  }
}

// group排序
GET /employ/_search
{
  "query": {
    "match": {
      "name":"邵哥"
    }
  }
  , "sort": [
    {
      "age": {
        "order": "desc"
      }
    }
  ],
  "aggs": {
    "group_by_age": {
      "terms": {
        "field": "age"
      }
    }
  }
}















