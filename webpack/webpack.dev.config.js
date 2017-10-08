const config = require('./webpack.common.config.js');
const path = require('path');

const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

config.module.rules.unshift(
    {
        test: /\.ts$/,
        use: ['awesome-typescript-loader', 'angular2-template-loader', 'angular-router-loader'],
        exclude: [/\.(spec|e2e)\.ts$/]
    }
);

config.devServer = {
    contentBase: path.join(__dirname, "../target/classes/static/"),
    proxy: {
        "/api": "http://127.0.0.1:8080/"
    },
    disableHostCheck: true,
    historyApiFallback: true,
    compress: true,
    port: 9000
};


config.plugins.push(
    new HtmlWebpackPlugin({
        template: path.join(__dirname, '../src/frontend/index.html.ejs'),
        favicon: path.join(__dirname, '../src/frontend/resources/favicons/favicon.ico'),
        filename: path.join(__dirname, '../target/classes/static/index.html'),
        inject: 'body',
        minify: {
            minifyCSS: true,
            minifyJS: true,
            removeComments: true,
            collapseWhitespace: true,
            collapseInlineTagWhitespace: true
        },
        chunksSortMode: 'dependency',
        dev: true
    }),
    new CleanWebpackPlugin(['resources'], {
        root: path.join(__dirname, '../target/classes/static')
    })
);

config.devtool = 'source-map';

module.exports = config;