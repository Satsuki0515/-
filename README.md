# 実装方法について

## 基本方針
各機能ごとにブランチを作成し、そのブランチ内でコードを編集するようにしてください。

## gitを使う前に
gitとgithubを連携するために、アクセストークンというものを作成する必要があります。  
このサイトを参考にして作成してください: https://capybara-notebook.com/github_accesstoken/

## gitの使い方
gitの使い方がよくわからない場合は、以下の手順に従ってやるとうまくいくと思います。
### gitの初期設定
1. 名前とメアドを設定するために、次のコードを実行してください:
```
git config --global user.name "[自分のGitHub上での名前]"
git config --global user.email "[自分がGitHubで使っているメアド]"
例: git config --global user.name "Melting-DSC"
```
2. 端末内でgithub上のコードをダウンロードしたい場所に移動してください。
3. 次のコードを実行してください。: 
```
git clone https://github.com/Satsuki0515/-.git
```
4. 正常にダウンロードできたら、次のコードを実行してください。:
```
cd ./-/
```
以上で初期設定は終わりです。

### ブランチの作成
1. 先程の初期設定で作成されたフォルダ内で、次のコードを実行してください。: 
```
git branch [自分が作る機能の名前(なるべく英語で)]
例: git branch feature/BGM
```
*※もしエラーメッセージが出た場合は、エラーメッセージの一番下に書いてあるコードを実行してください。  
こんなコードが書いてあるはずです:*
```
git config --global ~フォルダのパス~
```
2. 先ほど作成したブランチに移動するために、次のコードを実行してください。:
```
git checkout [作ったブランチの名前]
例: git checkout feature/BGM
```
以上でブランチ関連の操作は終わりです。コードを編集したり、ファイルを追加する場合は、**必ず**自分が作成したブランチに移動してから行ってください。

### 変更履歴の保存について
自分がなにかコードやファイルを変更した場合は、こまめに変更履歴を保存するようにしてください。  
あらかじめフォルダ内に".gitignore"という名前の**ファイル**を作成し、以下の内容をそのファイルの中にコピペしてください:
```
.gitignore
*.class
```
gitを使った変更履歴の保存方法は以下のとおりです。
1. 変更したファイルの準備をするために、次のコードを実行してください:
```
git add .
```
2. 変更内容を保存するために、次のコードを実行してください:
```
git commit -m "(自分がしたことのメモ。書ける場合は英語にしてください。難しい場合は日本語でも構いません。)"
```
以上で変更履歴を保存できます。  
あとで変更内容を見たい場合は、次のコードを実行してください:
```
git log
```
*logから抜け出せなくなった場合は、qキーを押してください。*

### 変更履歴のアップロード
その日の作業の終わりに、githubに変更履歴をアップロードするようにしてください。以下は、アップロードの手順です。
1. 自分が今いるブランチを確認するために、次のコードを実行してください:
```
git branch -a
(ブランチの一覧が出てきますが、自分が今いるブランチには直前に"*"がつきます。確認してください。)
```
2. 変更履歴をgithubにアップロードするために、次のコードを実行してください: 
```
git push -u origin [自分が作ったブランチの名前]
例: git push -u origin feature/BGM
```
このとき、ユーザー名とパスワードを求められることがあります。ユーザー名には自分がGitHubで使っている名前を、パスワードには先ほど作った**アクセストークン**を入力してください。

以上でアップロードは終わりです。作業が一段落したら、必ずアップロードするようにしてください。

### 機能の実装、マージについて

実装が終わり、テストも完全に終わったあと、自分が編集したものをメインのブランチと合体させることを「マージ」といいます。  
マージは、**機能の実装が一通り終わったあと**にやってください。以下はその方法です:
1. 変更履歴をアップロードしてください。アップロードの方法は上記を参考にしてください。
2. GitHub上で、自分が編集したブランチが正常にアップロードされているか確認してください。左上にブランチを切り替えるボタンがあるので、そこで自分のブランチに切り替えてください。
3. 自分のブランチの画面で、Compare & pull requestというボタンがあるので、そこを押してください。
4. Add a descriptionという場所に、自分がブランチで今までやったことを書いてください。
5. ページ下部にある、Create pull requestというボタンを押してください。
6. 変更の概要が表示されます。なるべく、**機能のテスト担当者**がここでもう一度テストを行ってください。
7. テストの結果、問題がなければ**機能のテスト担当者**がページ下部にあるMerge pull requestを実行してください。

以上でマージは終わりです。マージはその機能の完成のようなものなので、不具合がないかを慎重にチェックしてください。