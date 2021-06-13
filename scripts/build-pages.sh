#!/bin/bash

#
# Copyright 2020 Lars Tennstedt
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

mkdir pages
for dir in finnmath finnmath-kotest finnmath-assertj
do
  mkdir pages/$dir/
  cp -R $dir/build/docs/ pages/$dir/
  cp -R $dir/build/reports/ pages/$dir/
  rm -R pages/$dir/reports/project/*.txt
  cp -R $dir/build/dependencyUpdates/ pages/$dir/
  if [ $dir != 'finnmath-assertj' ]; then
    cp -R $dir/build/dokka/ pages/$dir/
    rm -R pages/$dir/reports/ktlint/
  fi
done
